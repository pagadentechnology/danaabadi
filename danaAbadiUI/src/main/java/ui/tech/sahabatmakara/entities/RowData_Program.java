package ui.tech.sahabatmakara.entities;

public class RowData_Program {
public String id, program_title, collected, target, desc, persen, url_img,
	total_donatur;
	
	public RowData_Program(String id, String program_title,String collected,
			String target,String persen, String desc, String url_img) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.program_title = program_title;
		this.collected = collected;
		this.target = target;
		this.persen = persen;
		this.desc = desc;
		this.url_img = url_img;
	}
	
	public RowData_Program(String id, String program_title,String collected,
			String target,String total_donatur,String persen, String desc, String url_img) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.program_title = program_title;
		this.collected = collected;
		this.target = target;
		this.total_donatur = total_donatur;
		this.persen = persen;
		this.desc = desc;
		this.url_img = url_img;
	}

}
