# Wildhacks Hack: Touche
Reppin' Purdue! Choo Choo! BTFU

![logo](http://blog.structurestudios.com/hubfs/00_images_ss2015/academic/icon-purdue.jpg?t=1470436930809)
![logo](http://blog.structurestudios.com/hubfs/00_images_ss2015/academic/icon-purdue.jpg?t=1470436930809)
![logo](http://blog.structurestudios.com/hubfs/00_images_ss2015/academic/icon-purdue.jpg?t=1470436930809)
![logo](http://blog.structurestudios.com/hubfs/00_images_ss2015/academic/icon-purdue.jpg?t=1470436930809)
## Inspiration

We were immediately excited by the possibility that the Tanvas hardware  and the intelligence of Clarifai presented.

## What it does

We wanted to know if it was possible to carry out image-processing flexibly from the device, specifically the Tanvas. On the Tanvas, users are able to select any image and experience the augmented touch sensation! They can feel what the photographer was feeling

## How we built it

We used a combination of Image-Processing algorithms, such as filters, segmentations, and cropping, to process images with the help of data from [Clarifai](https://clarifai.com/) and their Android Client. We didn't make a server, which cost us runtime, but it did make it easier to organize tasks and was much easier to learn.

## Theory and Application

The application works by applying image processing algorithms to an image file, namely any taken from the internet or gallery, and using the output of the algorithms as a base for the haptic resource overlay. The application first uses Clarifai to analyze the image for material tags such as, wood, brick, glass, etc, as well as identify color densities. Then, a color segmentation algorithm crops the image for the most common color densities. Theoretically, these cropped images will correspond to materials present in the image. These cropped images are re-sent to Clarifai to identify which color density corresponds to which material tag. Once the material images are identified, a specific material filter is applied to the region in which the material is identified for the purpose of a haptic overlay. Once all materials have had a texture filter applied to the haptic overlay for that region, the haptic overlays are cropped to one image with a generic grayscale version of the original image as a background layer to fill white space. The resulting image is a generally applied material overlay to color segmented regions. 

## Challenges we ran into

Before diving into the platform, we expected that Clarifai would provide us with location data rather than just a list of matched objects. To deal with this, we implemented various intermediate processing, mainly [Image-Segmentation](https://en.wikipedia.org/wiki/Image_segmentation). 

## Accomplishments that we're proud of ![star](http://www.mu.iastate.edu/media/cms/goldstar_icon_DCFACF6B21EC9.png)

Many different concepts were brought together in our final product. Developing each individually and then combining everything together was a good challenge. Especially, [the issue of working on the UIThread in Android](http://stackoverflow.com/questions/6343166/how-to-fix-android-os-networkonmainthreadexception). I swear, it was annoying, but at least easy to handle. #AsyncTaskAndThreads 

## What we learned

It takes a will before you get the feel of things down. It was touch-and-go for a while, we only had one experienced Java programmer, and had limited knowledge of Image Processing. We had some trouble deciding what to do, and the design decisions were very hastily made and re-made over and over again. The team also really made strides to learn Java and Advanced Image Processing, to the point it was mind blowing! Shout-out to our intern for join us at the last minute and always looking to help!! 

## What's next for Touche

Tanvas and Clarifiai each present many different ways to build from their platform. Building this implementation further could involve further development of the image-segmenting process involving more experimentation to see what sorts of input might allow us to most effectively identify objects and thus apply textures.
