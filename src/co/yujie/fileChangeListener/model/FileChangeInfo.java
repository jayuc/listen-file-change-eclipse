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
	
	private String projectName;
	
	private String projectLocation;

	public FileChangeInfo(String path, FileOperate opear, String type) {
		super();
		this.path = path;
		this.opear = opear;
		this.type = type;
	}

	public FileChangeInfo(String path, FileOperate opear, String type, String name, String storage, String location,
			String projectName, String projectLocation) {
		super();
		this.path = path;
		this.opear = opear;
		this.type = type;
		this.name = name;
		this.storage = storage;
		this.location = location;
		this.projectName = projectName;
		this.projectLocation = projectLocation;
	}

	@Override
	public String toString() {
		return "FileChangeInfo [path=" + path + ", opear=" + opear + ", type=" + type + ", name=" + name + ", storage="
				+ storage + ", location=" + location + ", projectName=" + projectName + ", projectLocation="
				+ projectLocation + "]";
	}

	public String getPath() {
		return path;
	}

	public FileOperate getOpear() {
		return opear;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getStorage() {
		return storage;
	}

	public String getLocation() {
		return location;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getProjectLocation() {
		return projectLocation;
	}
	
}
