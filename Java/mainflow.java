public class Main_Helper {
    void Main_Flow(Bitmap new_bitmap){
        Data Clarifai_Data = new Data;				//Get Clarifai Data
        Clarifai_Data = clarifai_image(new_bitmap); //Return Data
		int tag_count = count_tags(Clarifai_Data);	//get the amount of tags that are relavent to what we are looking for
		Bitmap[] segmented_images = new Bitmap[tag_count];	//instantiate bitmaps for segment images
		int[] color_density = int[tag_count];				//get color densities from clarifai
		Data[] clarifai_two_data = new Data[tag_count]		//setup for clarifai secondary calls
		
		for(int i = 0; i < tag_count; i++){
			segmented_images[i] = color_segment_image(color_density[i]);	//Set segmented bitmap images
			int material_type = clarifai_two_data[i] = clarifai_image(segmented_images[i]);		//Pull clarifai api to return clarifai data on segmented image
			apply_filters(segmented_images[i]);
		}
		combineimages(segmented_images);
		display!
    }
    void clarifai_image(Bitmap src){
        //Send bitmap to Clarifai
        //Return Color densities and tags
    }
	String count_tags(String[] tags){
		for each string in tags{
			if(string == some_tag)
				return some_tag;
		}
	}
}