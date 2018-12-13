package co.yujie.fileChangeListener.model;

/**
 * 文件修改描述类
 * @author yujie
 *
 */
public class FileChangeInfo {

	private String path;  //文件路径
	
	/**
	 * 文件操作类型
	 */
	private FileOperate opear;  
	
	private String type;  //文件类型
	
	private String name;
	
	private String storage;   //文件存储位置
	
	private String location;   //文件绝对路径

	public FileChangeInfo(String path, FileOperate opear, String type) {
		super();
		this.path = path;
		this.opear = opear;
		this.type = type;
	}
	
	public FileChangeInfo(String path, FileOperate opear, String type, String name, String storage, String location) {
		super();
		this.path = path;
		this.opear = opear;
		this.type = type;
		this.name = name;
		this.storage = storage;
		this.location = location;
	}

	@Override
	public String toString() {
		return "FileChangeInfo [path=" + path + ", opear=" + opear + ", type=" + type + ", name=" + name + ", storage="
				+ storage + ", location=" + location + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public FileOperate getOpear() {
		return opear;
	}

	public void setOpear(FileOperate opear) {
		this.opear = opear;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
