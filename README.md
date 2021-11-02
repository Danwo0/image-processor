# CS3500

This project is our take on an Image Processor. It is currently able to load images, modify using various filters, and save images. Loading and
saving images is done using the ppm format, the processor currently cannot output in other formats.

The processor is split into three packages, a view, a controller, and a model.

Each of the three packages contains an interface and an implementation of said interface. Each interface defines the operations expected of
the three respective packages. The currently implementations of each interface are limited to the processing of ppm files and applying various
filters to them.

The view, while currently functional, contains no functions other than renderMessage, which appends a message to the destination.

Our controller currently serves as a human interface, it takes in a model and view, and queries the user for commands. This is done through the
startProcessor method, which constantly queries the user so long as they have not quit. Each time the user makes an input, the controller
compares the input with a map of all known commands, and executes based on their input. The user may quit at any time in the loop using q as
their input.

The model does any processing of the actual image. The provided image is loaded into the model based on a path which the user passes
through the controller. The model can apply filters using its public methods, which stores the resultant images within the model. A user can
retrieve images from the model through the controller using a save command.

We have an enum GreyscaleMode, which defines any mode which the user can apply a greyscale filter to their image with.
There is also a main, located within ImageProcessor.java, which launches an instance of ImageProcessor.

Each of the three packages has their corresponding tests located within the test folder.
