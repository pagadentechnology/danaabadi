package ui.tech.sahabatmakara.entities;

public class RowData_ListGallery {
	public String id,title,url_img, img_name;
	
	public RowData_ListGallery(String id,String title,String url_img, String img_name) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.title = title;
		this.url_img = url_img;
		this.img_name = img_name;
	}

	public RowData_ListGallery(String id,String title,String url_img) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.title = title;
		this.url_img = url_img;
	}

}
