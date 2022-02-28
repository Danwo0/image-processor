# Image Processor for CS3500

UPDATE HW5:
The controller and model should be complete and fully functional; view still contains the bare minimum of printing out the feedback message
in the console as it did for HW4. For HW5, we made 2 major implementations: support for conventional image formats, and filter/transform.

The project now supports PNG, JPG, and BMP while still supporting PPM. In order to accomplish this, "two" new publics has been added to the 
model interface - savePPM(String) and loadImage(BufferedImage, String). 

We initially tried to create a new implementation of the existing interface that works with BufferedImages while extending or having the 
previous implementation, but since we already had the image represented as a 2D array in the old implementation, we decided to simply add 
new features that would accept BufferedImage and would transform it into a 2D array of RGB pixels. loadImage(BufferedImage, String) 
accomplishes this operation in the model.

The old saveImage method got turned into savePPM, a "new" public method; the saveImage method now returns a BufferedImage, which does the 
opposite of what loadImage is doing. 

One major change we made in progress was moving image IO into the controller. We previously had loading image done in the model and saving 
done in the controller; now both input and output is done in the controller. Controller will now generate a BufferedImage or String from 
the image path and sent it directly to the model to transform into a 2D array. Similarly, the model will transform the 2D array into a
BufferedImage and String and sends it directly to the controller for it to output as a file through ImageIO.

Second implementation was the addition of filter and transform. There are also done as an additional method inside the Model interface.
Both filter and transform will accept an enum value for the appropriate filter matrix or transform matrix from the controller, which is
used by a private method to get the appropriate matrix depending on the enum value inside the model. The filter and transform method
should work with all matrices as long as they are properly constructed, which should be enforced since they are a set value. 
Both blur and sharpen is implemented using filter() and all the greyscale (except for max value) and sepia are implemented using
transform(). as a result, old greyscale method has been renamed to value() and only does max value greyscale transform.


HW4: 
This project is our take on an Image Processor. It is currently able to load images, modify using various filters, and save images. Loading and
saving images is done using the ppm format, the processor currently cannot output in other formats.

The processor is split into three packages, a view, a controller, and a model.

Each of the three packages contains an interface and an implementation of said interface. Each interface defines the operations expected of
the three respective packages. The currently implementations of each interface are limited to the processing of ppm files and applying various
filters to them.

The view, while currently functional, contains no functions other than renderMessage, which appends a message to the destination.

Our controller currently serves as a human interface, it takes in a model and view, and queries the user for commands. This is done through the
startProcessor method, which constantly queries the user so long as they have not quit. Each time the user makes an input, the controller
compares the input with a map of all known commands, and executes based on their input. The user may quit by entering q instead of an operation.
The user entering q mid command will not be counted as a quit command but instead will be passed as the command argument.
	
Controller package also contains the comands package, which contains all known commands for the controller. The interface ImageProcessorCommand
contains methods for completing the operation by delegating to the given model and also a method for rendering a message based on the
results of the operations by delegating to the given view. Also, Save class will create a new image or overwrite using FileWriter.

The model does any processing of the actual image. The provided image is loaded into the model based on a path which the user passes
through the controller. The model can apply filters using its public methods, which stores the resultant images within the model. A user can
retrieve images from the model through the controller using a save command.

We have an enum GreyscaleMode, which defines any mode which the user can apply a greyscale filter to their image with.
There is also a main, located within ImageProcessor.java, which launches an instance of ImageProcessor.

Each of the three packages has their corresponding tests located within the test folder. Controller tests uses respective mocks.

For example, type this script into the console when the program runs: 

load res/pix.ppm testimage
save res/testsave.ppm testimage
vflip testimage testvflip
hflip testvflip testvhflip
brighten 2 testimage testbrighten
value-component testimage testvalcomp
red-component testimage testred
luma testimage testluma
save res/testvflip.ppm testvflip
save res/testvhflip.ppm testvhflip
save res/testbrighten.ppm testbrighten
save res/testvalcomp.ppm testvalcomp
save res/testred.ppm testred
save res/testluma.ppm testluma
quit

This script should go through the major operations and create new files or overwritte
the output image files starting with "test...".
