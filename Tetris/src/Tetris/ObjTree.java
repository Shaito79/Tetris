package Tetris;

public class ObjTree {
	private static final ObjTree objTree = new ObjTree();
	
	
	private ObjTree() {
	}
	
	public static ObjTree getObjTree() {
		return objTree;
	}

}
