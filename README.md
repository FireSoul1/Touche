# Wildhacks Hack: Feelin' Clearly Now
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

We used Image-Segmenting algorithms to pre-process images with the help of data from [Clarifai](https://clarifai.com/) and their Android Client. We didn't make a server, which cost us runtime, but it did make it easier to organize tasks and was easier for the Java newbies to pick things up. 

## Challenges we ran into

Before diving into the platform, we expected that Clarifai would provide us with location data rather than just a list of matched objects. To deal with this, we implemented various intermediate processing, mainly [Image-Segmentation](https://en.wikipedia.org/wiki/Image_segmentation). 

## Accomplishments that we're proud of ![star](http://www.mu.iastate.edu/media/cms/goldstar_icon_DCFACF6B21EC9.png)

Many different concepts were brought together in our final product. Developing each individually and then combining everything together was a good challenge. Especially, [the issue of working on the UIThread in Android](http://stackoverflow.com/questions/6343166/how-to-fix-android-os-networkonmainthreadexception). I swear, it was annoying, but at least easy to handle. #AsyncTaskAndThreads 

## What we learned

It takes a will before you get the feel of things down. It was touch-and-go for a while, we only had one experienced Java programmer, and had limited knowledge of Image Processing. We had A LOT of trouble deciding what to do, and the design decisions were very hastily made and re-made over and over again. The team also really made strides to learn Java and Image Processing, to the point it was mind blowing! Shout-out to our intern for join us at the last minute and always looking to help!! 

## What's next for Feelin' Clearly Now

Tanvas and Clarifiai each present many different ways to build from their platform. Building this implementation further could involve further development of the image-segmenting process involving more experimentation to see what sorts of input might allow us to most effectively identify objects and thus apply textures.
