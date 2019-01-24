import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ResultsComparator {

    public static void log(String s) {
    //    System.out.println(s);
    }


    final static int MIN_ARGS_LENGTH = 2;
    static void printUsage() {
        System.out.println("<sourceDirectory> <resultsDirectory> [[test]...|ALL]");
    }

    /**
     * Main method for testing.  Every class can have a main
     * method in Java
     */
    public static void main(String[] args) {
        // uncomment a call here to run a test and comment
        // out the ones you don't want to run

        if (args.length < MIN_ARGS_LENGTH) {
            printUsage();
        }

        ResultsComparator rc = new ResultsComparator(args[0], args[1]);

        if (args.length > MIN_ARGS_LENGTH) {
            if (args[MIN_ARGS_LENGTH].equals("ALL")) {
                rc.testAll();
            } else {
                for (int i=MIN_ARGS_LENGTH; i<args.length; ++i) {
                    rc.test(args[i]);
                }
            }
        } else {
            rc.runInteractive();
        }
    }

    Path sourceDir;
    Path resultsDir;
    boolean keepGoingOnFailure=true;
    ResultsComparator(String sourceDir, String resultsDir) {
        this.sourceDir = Paths.get(sourceDir);
        this.resultsDir = Paths.get(resultsDir);
    }

    boolean test(String testName, String imageName) {
        String testParam = null;
        {
            String []testComponents = testName.split("_");
            if (testComponents.length > 1) {
                testName = testComponents[0];
                testParam = testComponents[1];
            }
        }
        if (!imageName.endsWith(".jpg")) {
            imageName += ".jpg";
        }
        log("compare testName="+testName+" imageName="+imageName);
        Path testResultsPath = resultsDir.resolve(testName).resolve(imageName);
        Path sourceImagePath = sourceDir.resolve(imageName);
        Picture groundTruthPicture = new Picture(testResultsPath.toString());
        Picture sourcePicture = new Picture(sourceImagePath.toString());

        java.lang.reflect.Method method;
        try {
            if (testParam!=null) {
                method = sourcePicture.getClass().getMethod(testName, int.class);
            } else {
                method = sourcePicture.getClass().getMethod(testName);
            }
        } catch (SecurityException e) {
            System.err.println(e);
            return false;
        } catch (NoSuchMethodException e) {
            System.err.println("Expected to find a method named "+testName);
            System.err.println("Make sure to specify a test that has a corresponding method that is implemented in Picture");
            System.err.println(e);
            return false;
        }

        try {
            if (testParam != null) {
                int intArg;
                try {
                    intArg = Integer.parseInt(testParam);
                } catch (RuntimeException x) {
                    System.err.println("Could not parse test parameter " + testParam + " as an int.");
                    System.err.println("The test infrastructure currently only supports passing arguments of type int");
                    return false;
                }
                method.invoke(sourcePicture, intArg);
            } else {
                method.invoke(sourcePicture);
            }
        } catch (ReflectiveOperationException x) {
            System.err.println("Could not run "+method);
            System.err.print(x);
        }

        return groundTruthPicture.equals(sourcePicture);
    }


    boolean test(String testName) {
        try {
            int numSuccesses=0;
            int numTotal=0;
            Path testResultsPath = resultsDir.resolve(testName);
            DirectoryStream<Path> stream =
                    Files.newDirectoryStream(testResultsPath, "*.jpg");
            for (Path pictureFilename: stream) {
                boolean result = test(testName, pictureFilename.getFileName().toString());
                if (result) { numSuccesses++; }
                numTotal++;
            }
            System.out.println("Test: "+testName+" --> "+numSuccesses+"/"+numTotal);
            return numTotal == numSuccesses;
        } catch (IOException x) {
            System.err.println(x);
            return false;
        }
    }

    boolean testAll() {
        try {
            int numSuccesses=0;
            int numTotal=0;
            DirectoryStream<Path> stream =
                    Files.newDirectoryStream(resultsDir,
                            nestedPath -> { return (Files.isDirectory(nestedPath)); });
            for (Path entry : stream) {
                String name = entry.getFileName().toString();
                log("name="+name);
                boolean result = test(name);
                if (result) { numSuccesses++; }
                numTotal++;
            }
            System.out.println("Summary: "+numSuccesses+"/"+numTotal);
            return numTotal == numSuccesses;
        } catch (IOException x) {
            System.err.println(x);
            return false;
        }
    }


    void runInteractive() {
        java.util.Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("Enter the test name (to test against every image),"+
                    " or test/filename to test a specific image or \"quit\" to exit)");
            String testname = s.nextLine();
            if (testname.equals("quit")) {
                break;
            }
            if (testname.indexOf('/')>=0) {
                String []parts = testname.split("/");
                if (parts.length != 2 ||
                        parts[0].length() == 0 ||
                        parts[1].length() == 0)
                {
                    test(parts[0], parts[1]);
                } else {
                    System.err.println("Please specify TESTNAME/IMAGENAME");
                }
            } else {
                test(testname);
            }
        }
    }

}