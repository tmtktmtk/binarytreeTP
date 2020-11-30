package fr.istic.prg1.tree;

//import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
//import java.io.PrintStream;

import fr.istic.prg1.tree_util.AbstractImage;
import fr.istic.prg1.tree_util.Iterator;
import fr.istic.prg1.tree_util.Node;

public class Testing {

	/**
	 * @param fileName nom du fichier à lire
	 * @return Image créée à partir du fichier
	 */
	public static Image readFile(String fileName) {
		Image image = new Image();
		try {
			image.xCreateTreeFromFile(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return (Image) image;
	}

	public static boolean isEqual(AbstractImage image1,AbstractImage image2) {
		Iterator<Node> it1 = image1.iterator();
		Iterator<Node> it2 = image2.iterator();
		return isEqual(it1,it2,0);
	}

	private static boolean isEqual(Iterator<Node> it1, Iterator<Node> it2,int depth) {
		if (it1.isEmpty() && it2.isEmpty())
			return true;
		if (it1.isEmpty() ^ it2.isEmpty()) {
			System.out.println("case2");
			return false;
		}
		if (it1.getValue().state == it2.getValue().state) {
			it1.goLeft();
			it2.goLeft();
			depth++;
			boolean left = isEqual(it1, it2, depth);
			it1.goUp();
			it2.goUp();
			it1.goRight();
			it2.goRight();
			boolean right = isEqual(it1, it2,depth);
			it1.goUp();
			it2.goUp();
			depth--;
			return (left && right);
		}
		System.out.println(it1.getValue().state+"-"+it2.getValue().state+"-"+depth);
		return false;
	}

//	private static int major(Iterator<Node> it) {
//		it.goLeft();
//		int lnode = it.getValue().state;
//		it.goUp();
//		it.goRight();
//		int rnode = it.getValue().state;
//		it.goUp();
//		int res = 0;
//		if (lnode == 1 || rnode == 1)
//			res = 1;
//		else if ((lnode == 0 && rnode == 2) || (lnode == 2 && rnode == 0))
//			res = 0;
//		else {
//			it.goLeft();
//			int lchild = major(it);
//			it.goUp();
//			it.goRight();
//			int rchild = major(it);
//			it.goUp();
//			if (lchild == rchild)
//				res = lchild;
//			else
//				res = 1;
//		}
//		return res;
//	}

	public static void PreOrderT(AbstractImage image) {
		Iterator<Node> it = image.iterator();
		PreOrderT(it);
	}

	static int d = 0;

	private static void PreOrderT(Iterator<Node> it) {
		if (!it.isEmpty()) {
			System.out.print("[" + it.getValue().state + "]");
			it.goLeft();
			PreOrderT(it);
			it.goUp();
			it.goRight();
			PreOrderT(it);
			it.goUp();
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
//		Image image1 = readFile("a5.arb");
//		Image i1 = new Image();
//		Iterator<Node> it1 = i1.iterator();
//		it1.addValue(Node.valueOf(2));
//		it1.goLeft();
//		it1.addValue(Node.valueOf(1));
//		it1.goUp();
//		it1.goRight();
//		it1.addValue(Node.valueOf(0));
//		it1.goUp();
//		Image i2 = new Image();
//		Iterator<Node> it2 = i2.iterator();
//		it2.addValue(Node.valueOf(2));
//		it2.goLeft();
//		it2.addValue(Node.valueOf(1));
//		it2.goUp();
//		it2.goRight();
//		it2.addValue(Node.valueOf(0));
//		it2.goUp();
//		i2.zoomOut(i1);
//		PreOrderT(i1);
//		System.out.println();
//		PreOrderT(i2);
//		System.out.println();
//		System.out.println(i1.isIncludedIn(i2));
//		boolean result = image1.sameLeaf(128, 192, 191, 255);
//		int result = major(image1.iterator());
//		System.out.println(result);
		Image i1 = readFile("cartoon.arb");
		Image i2 = readFile("cartoonout.arb");
		Image ires = new Image();
		ires.zoomOut(i1);
//
//		File file1 = new File("ires.txt");
//		PrintStream stream = new PrintStream(file1);
//		System.setOut(stream);		
//		PreOrderT(ires);
		System.out.println(isEqual(ires, i2));
	}
}
