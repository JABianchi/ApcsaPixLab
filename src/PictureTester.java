/**
 * This class contains class (static) methods
 * that will help you test the Picture class 
 * methods.  Uncomment the methods and the code
 * in the main to test.
 * 
 * @author Barbara Ericson 
 */
public class PictureTester
{
  /** Method to test zeroBlue */
  public static void testZeroBlue()
  {
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  
  /** Method to test keepOnlyBlue [IMPLEMENTED A5 (3)]  */
  
  /** Method to test keepOnlyGreen [EC] */
    
  /** Method to test keepOnlyRed [EC] */
  
  /** Method to test negate [IMPLEMENTED A5 (4)]*/

  /** Method to test grayscale [IMPLEMENTED A5 (5)]*/
  
  /** Method to test fixUnderwater [IMPLEMENTED A5 (6) EC]*/
  
  /** Method to test mirrorVertical */
  public static void testMirrorVertical()
  {
    Picture caterpillar = new Picture("caterpillar.jpg");
    caterpillar.explore();
    caterpillar.mirrorVertical();
    caterpillar.explore();
  }
  
  /** Method to test mirrorVerticalRightToLeft [IMPLEMENTED A6 (1) ] */
  
  /** Method to test mirrorHorizontal [IMPLEMENTED A6 (2) ]*/
  
  /** Method to test mirrorHorizontalBotToTop [IMPLEMENTED A6 (3) ]*/
  
  /** Method to test mirrorDiagonal [IMPLEMENTED A6 (4) EC]*/
  
  /** Method to test mirrorTemple */
  public static void testMirrorTemple()
  {
    Picture temple = new Picture("temple.jpg");
    temple.explore();
    temple.mirrorTemple();
    temple.explore();
  }
  
  /** Method to test mirrorArms [IMPLEMENTED A7 (2) ]*/
  
  /** Method to test mirrorGull  [IMPLEMENTED A7 (3) ]*/
  
  /** Method to test the collage method */
  public static void testCollage()
  {
    Picture canvas = new Picture("640x480.jpg");
    canvas.createCollage();
    canvas.explore();
  }
  
  /** Method to test copy  [IMPLEMENTED A8 (1) ]*/       
  
  /** Method to test edgeDetection */
  public static void testEdgeDetection()
  {
    Picture swan = new Picture("swan.jpg");
    swan.edgeDetection(10);
    swan.explore();
  }
  
  /** Method to test edgeDetection2  [IMPLEMENTED A9 (2) ]*/
  

  /** Main method for testing.  Every class can have a main
    * method in Java */
  public static void main(String[] args) { 
    // uncomment a call here to run a test and comment
    // out the ones you don't want to run
    
    testZeroBlue(); 
    //testKeepOnlyBlue();
    //testKeepOnlyRed();
    //testKeepOnlyGreen();
    //testNegate();
    //testGrayscale();
    //testFixUnderwater();
    //testMirrorVertical();
    //testMirrorVerticalRightToLeft();
    //testMirrorHorizontal();
    //testMirrorHorizontalBotToTop();
    //testMirrorDiagonal();
    //testMirrorTemple();
    //testMirrorArms();
    //testMirrorGull();
    //testCollage();
    //testCopy();
    //testEdgeDetection();
    //testEdgeDetection2();

  }
}