package fr.istic.prg1.tree;

import java.util.Scanner;

import fr.istic.prg1.tree_util.AbstractImage;
import fr.istic.prg1.tree_util.Iterator;
import fr.istic.prg1.tree_util.Node;
//import fr.istic.prg1.tree_util.NodeType;

/**
 * @author TRUONG K.M. Tam
 * @version 1.0
 * @since 2020-11-18
 * 
 *        Classe décrivant les images en noir et blanc de 256 sur 256 pixels
 *        sous forme d'arbres binaires.
 * 
 */

public class Image extends AbstractImage {
	private static final Scanner standardInput = new Scanner(System.in);

	public Image() {
		super();
	}

	public static void closeAll() {
		standardInput.close();
	}

	/**
	 * @param x abscisse du point
	 * @param y ordonnée du point
	 * @pre !this.isEmpty()
	 * @return true, si le point (x, y) est allumé dans this, false sinon
	 */
	@Override
	public boolean isPixelOn(int x, int y) {
		Iterator<Node> it = this.iterator();
		int midx = 256 / 2;
		int midy = 256 / 2;
		int halfcut = 256 / 4;
		boolean hrzn = true;
		while (it.getValue().state == 2) {
			if (hrzn) {
				if (y < (midy)) {
					it.goLeft();
					midy = midy - halfcut;
				} else {
					it.goRight();
					midy = midy + halfcut;
				}
			} else {
				if (x < (midx)) {
					midx = midx - halfcut;
					it.goLeft();
				} else {
					midx = midx + halfcut;
					it.goRight();
				}
				halfcut = halfcut / 2;
			}
			hrzn = !hrzn;
		}
		return it.getValue().state == 1;
	}

	/**
	 * this devient identique à image2.
	 *
	 * @param image2 image à copier
	 *
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void affect(AbstractImage image2) {
		Iterator<Node> it1 = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		it1.clear();
		affectAux(it1, it2);
	}

	private void affectAux(Iterator<Node> it1, Iterator<Node> it2) {
		it1.addValue(it2.getValue());
		if (it2.getValue().state == 2) {
			it1.goLeft();
			it2.goLeft();
			affectAux(it1, it2);
			it1.goUp();
			it2.goUp();
			it1.goRight();
			it2.goRight();
			affectAux(it1, it2);
			it1.goUp();
			it2.goUp();
		}
	}

	/**
	 * this devient rotation de image2 à 180 degrés.
	 *
	 * @param image2 image pour rotation
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void rotate180(AbstractImage image2) {
		Iterator<Node> it1 = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		it1.clear();
		rotate180(it1, it2);
	}

	private void rotate180(Iterator<Node> it1, Iterator<Node> it2) {
		it1.addValue(it2.getValue());
		if (it2.getValue().state == 2) {
			it1.goRight();
			it2.goLeft();
			rotate180(it1, it2);
			it1.goUp();
			it2.goUp();
			it1.goLeft();
			it2.goRight();
			rotate180(it1, it2);
			it1.goUp();
			it2.goUp();
		}
	}

	/**
	 * this devient rotation de image2 à 90 degrés dans le sens des aiguilles
	 * d'une montre.
	 *
	 * @param image2 image pour rotation
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void rotate90(AbstractImage image2) {
		Iterator<Node> it1 = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		it1.clear();
		rotate90(it1, it2);
	}

	// rotate clockwise
	// this-upperleft = image-lowerleft; this-upperright = image-upperleft
	// this-lowerleft = image-lowerright; this-lowerright = image-upperright
	private void rotate90(Iterator<Node> it1, Iterator<Node> it2) {
		it1.addValue(it2.getValue());
		if (it2.getValue().state == 2) {
			///////////////
			// UPPER HALF//
			///////////////
			// go to the upper left quarter of this//
			it1.goLeft();
			it1.addValue(Node.valueOf(2));
			it1.goLeft();
			// add to upper left of this lower left of image2
			it2.goRight();
			if (it2.getValue().state != 2) {
				it1.addValue(it2.getValue());
				it2.goUp(); // return to initial position
			} else {
				it2.goLeft();
				rotate90(it1, it2);
				it2.goUp();
				it2.goUp();// it2
			}
			int lnode = it1.getValue().state;
			// go to the upper right////
			it1.goUp();
			it1.goRight();
			it2.goLeft();
			if (it2.getValue().state != 2) {
				it1.addValue(it2.getValue());
				it2.goUp(); // it2 return
			} else {
				it2.goLeft();
				rotate90(it1, it2);
				it2.goUp();
				it2.goUp();// it2 return to initial position
			}
			int rnode = it1.getValue().state;
			it1.goUp();
			// node simplification
			if (rnode != 2 && rnode == lnode) {
				it1.clear();
				it1.addValue(Node.valueOf(rnode));
			}
			it1.goUp();// it1 return to initial position
			//////////////
			// LOWER HALF//
			//////////////
			// go to lower left//
			it1.goRight();
			it1.addValue(Node.valueOf(2));
			it1.goLeft();
			it2.goRight();
			if (it2.getValue().state != 2) {
				it1.addValue(it2.getValue());
				it2.goUp(); // it2 return to initial position
			} else {
				it2.goRight();
				rotate90(it1, it2);
				it2.goUp();
				it2.goUp();// it2 return to initial position
			}
			lnode = it1.getValue().state;
			// go to lower right of this//
			it1.goUp();
			it1.goRight();
			it2.goLeft();
			if (it2.getValue().state != 2) {
				it1.addValue(it2.getValue());
				it2.goUp(); // it2 return to initial position
			} else {
				it2.goRight();
				rotate90(it1, it2);
				it2.goUp();
				it2.goUp();// it2 return to initial position
			}
			rnode = it1.getValue().state;
			it1.goUp();
			// node simplification
			if (rnode != 2 && rnode == lnode) {
				it1.clear();
				it1.addValue(Node.valueOf(rnode));
			}
			it1.goUp();// it1 return
		}
	}

	/**
	 * this devient inverse vidéo de this, pixel par pixel.
	 *
	 * @pre !image.isEmpty()
	 */
	@Override
	public void videoInverse() {
		Iterator<Node> it = this.iterator();
		videoInverse(it);
	}

	private void videoInverse(Iterator<Node> it) {
		switch (it.getValue().state) {
		case 0:
			it.setValue(Node.valueOf(1));
			break;
		case 1:
			it.setValue(Node.valueOf(0));
			break;
		case 2:
			it.goLeft();
			videoInverse(it);
			it.goUp();
			it.goRight();
			videoInverse(it);
			it.goUp();
			break;
		}
	}

	/**
	 * this devient image miroir verticale de image2.
	 *
	 * @param image2 image à agrandir
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void mirrorV(AbstractImage image2) {
		Iterator<Node> it1 = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		it1.clear();
		mirror(it1, it2, false);
	}
	
	/**
	 * @param it1 Iterator 1
	 * @param it2 Iterator 2
	 * @param cuthrzn Direction de la coupe, true: horizonale; false: verticale
	 */
	private void mirror(Iterator<Node> it1, Iterator<Node> it2, boolean cuthrzn) {
		it1.addValue(it2.getValue());
		if (it2.getValue().state == 2) {
			if (cuthrzn) {
				it1.goLeft();
				it2.goLeft();
				mirror(it1, it2, !cuthrzn);
				it1.goUp();
				it2.goUp();
				it1.goRight();
				it2.goRight();
				mirror(it1, it2, !cuthrzn);
				it1.goUp();
				it2.goUp();
			} else {
				it1.goLeft();
				it2.goRight();
				mirror(it1, it2, !cuthrzn);
				it1.goUp();
				it2.goUp();
				it1.goRight();
				it2.goLeft();
				mirror(it1, it2, !cuthrzn);
				it1.goUp();
				it2.goUp();
			}
		}

	}

	/**
	 * this devient image miroir horizontale de image2.
	 *
	 * @param image2 image à agrandir
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void mirrorH(AbstractImage image2) {
		Iterator<Node> it1 = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		it1.clear();
		mirror(it1, it2, true);
	}

	/**
	 * this devient quart supérieur gauche de image2.
	 *
	 * @param image2 image à agrandir
	 * 
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void zoomIn(AbstractImage image2) {
		Iterator<Node> it1 = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		it1.clear();
		if (it2.getValue().state == 2) {
			it2.goLeft();
		}
		if (it2.getValue().state == 2) {
			it2.goLeft();
		}
		this.affectAux(it1, it2);
	}

	/**
	 * Le quart supérieur gauche de this devient image2, le reste de this devient
	 * éteint.
	 * 
	 * @param image2 image à réduire
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void zoomOut(AbstractImage image2) {
		Iterator<Node> it1 = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		it1.clear();
		it1.addValue(Node.valueOf(2));
		it1.goRight();
		it1.addValue(Node.valueOf(0));
		it1.goUp();
		it1.goLeft();
		it1.addValue(Node.valueOf(2));
		it1.goRight();
		it1.addValue(Node.valueOf(0));
		it1.goUp();
		it1.goLeft();
		this.zoomOut(it1, it2, 2);
		if (it1.getValue().state == 0) {
			it1.goRoot();
			it1.clear();
			it1.addValue(Node.valueOf(0));
		}
	}

	private void zoomOut(Iterator<Node> it1, Iterator<Node> it2, int depth) {
		if (it2.getValue().state == 2 && depth < 16) {
			it1.addValue(it2.getValue());
			it1.goLeft();
			it2.goLeft();
			depth++;
			zoomOut(it1, it2, depth);
			int lnode = it1.getValue().state;
			it1.goUp();
			it2.goUp();
			it1.goRight();
			it2.goRight();
			zoomOut(it1, it2, depth);
			int rnode = it1.getValue().state;
			it1.goUp();
			it2.goUp();
			depth--;
			if (lnode != 2 && lnode == rnode) {
				it1.clear();
				it1.addValue(Node.valueOf(lnode));
			}
		} else {
			if (it2.getValue().state == 2) {
				int major = major(it2);
				it1.addValue(Node.valueOf(major));
			} else
				it1.addValue(it2.getValue());
		}
	}

	private int major(Iterator<Node> it) {
		it.goLeft();
		int lnode = it.getValue().state;
		it.goUp();
		it.goRight();
		int rnode = it.getValue().state;
		it.goUp();
		int res = 0;
		if (lnode == 1 || rnode == 1)
			res = 1;
		else if ((lnode == 0 && rnode == 2) || (lnode == 2 && rnode == 0))
			res = 0;
		else {
			it.goLeft();
			lnode = major(it);
			it.goUp();
			it.goRight();
			rnode = major(it);
			it.goUp();
			if (lnode == rnode)
				res = lnode;
			else
				res = 1;
		}
		return res;
	}

	/**
	 * this devient l'intersection de image1 et image2 au sens des pixels allumés.
	 * 
	 * @pre !image1.isEmpty() && !image2.isEmpty()
	 * 
	 * @param image1 premiere image
	 * @param image2 seconde image
	 */
	@Override
	public void intersection(AbstractImage image1, AbstractImage image2) {
		Iterator<Node> it = this.iterator();
		Iterator<Node> it1 = image1.iterator();
		Iterator<Node> it2 = image2.iterator();
		it.clear();
		intersection(it, it1, it2);
	}

	private void intersection(Iterator<Node> it, Iterator<Node> it1, Iterator<Node> it2) {
		int state1 = it1.getValue().state;
		int state2 = it2.getValue().state;
		if (state1 == 0 || state2 == 0) {
			it.addValue(Node.valueOf(0));
		} else if (state1 == 1 && state2 == 1) {
			it.addValue(Node.valueOf(1));
		} else if (state1 == 2 && state2 == 1) {
			this.affectAux(it, it1);
		} else if (state1 == 1 && state2 == 2) {
			this.affectAux(it, it2);
		} else {
			it.addValue(Node.valueOf(2));
			goLeft(it, it1, it2);
			intersection(it, it1, it2);
			int lnode = it.getValue().state;
			goUp(it, it1, it2);
			goRight(it, it1, it2);
			intersection(it, it1, it2);
			int rnode = it.getValue().state;
			goUp(it, it1, it2);
			if (lnode != 2 && lnode == rnode) {
				it.clear();
				it.addValue(Node.valueOf(lnode));
			}
		}
	}

	private void goRight(Iterator<Node> it, Iterator<Node> it1, Iterator<Node> it2) {
		it.goRight();
		it1.goRight();
		it2.goRight();
	}

	private void goUp(Iterator<Node> it, Iterator<Node> it1, Iterator<Node> it2) {
		it.goUp();
		it1.goUp();
		it2.goUp();
	}

	private void goLeft(Iterator<Node> it, Iterator<Node> it1, Iterator<Node> it2) {
		it.goLeft();
		it1.goLeft();
		it2.goLeft();
	}

	/**
	 * this devient l'union de image1 et image2 au sens des pixels allumés.
	 * 
	 * @pre !image1.isEmpty() && !image2.isEmpty()
	 * 
	 * @param image1 premiere image
	 * @param image2 seconde image
	 */
	@Override
	public void union(AbstractImage image1, AbstractImage image2) {
		Iterator<Node> it = this.iterator();
		Iterator<Node> it1 = image1.iterator();
		Iterator<Node> it2 = image2.iterator();
		it.clear();
		union(it, it1, it2);
	}

	private void union(Iterator<Node> it, Iterator<Node> it1, Iterator<Node> it2) {
		int state1 = it1.getValue().state;
		int state2 = it2.getValue().state;
		if (state1 == 1 || state2 == 1) {
			it.addValue(Node.valueOf(1));
		} else if (state1 == 0 && state2 == 0) {
			it.addValue(Node.valueOf(0));
		} else if (state1 == 2 && state2 == 0) {
			this.affectAux(it, it1);
		} else if (state1 == 0 && state2 == 2) {
			this.affectAux(it, it2);
		} else {
			it.addValue(Node.valueOf(2));
			goLeft(it, it1, it2);
			union(it, it1, it2);
			int lnode = it.getValue().state;
			goUp(it, it1, it2);
			goRight(it, it1, it2);
			union(it, it1, it2);
			int rnode = it.getValue().state;
			goUp(it, it1, it2);
			if (lnode != 2 && lnode == rnode) {
				it.clear();
				it.addValue(Node.valueOf(lnode));
			}
		}
	}

	/**
	 * Attention : cette fonction ne doit pas utiliser la commande isPixelOn
	 * 
	 * @return true si tous les points de la forme (x, x) (avec 0 <= x <= 255)
	 *         sont allumés dans this, false sinon
	 */
	@Override
	public boolean testDiagonal() {
		Iterator<Node> it = this.iterator();
		return testDiagonal(it);
	}

	private boolean testDiagonal(Iterator<Node> it) {
		boolean res = true;
		if (res) {
			switch (it.getValue().state) {
			case 0:
				res = false;
				break;
			case 1:
				break;
			case 2:
				it.goLeft();
				int state = it.getValue().state;
				if (state == 2) {
					it.goLeft();
					res = res && testDiagonal(it);
					it.goUp();
				} else
					res = res && (state == 1);
				it.goUp();
				it.goRight();
				state = it.getValue().state;
				if (state == 2) {
					it.goRight();
					res = res && testDiagonal(it);
					it.goUp();
				} else
					res = res && (state == 1);
				it.goUp();
				break;
			}
		}
		return res;
	}

	/**
	 * @param x1 abscisse du premier point
	 * @param y1 ordonnée du premier point
	 * @param x2 abscisse du deuxième point
	 * @param y2 ordonnée du deuxième point
	 * @pre !this.isEmpty()
	 * @return true si les deux points (x1, y1) et (x2, y2) sont représentés par
	 *         la même feuille de this, false sinon
	 */
	@Override
	public boolean sameLeaf(int x1, int y1, int x2, int y2) {
		Iterator<Node> it = this.iterator();
		boolean cuthrzn = true; // true: decoupe horizontal, false: decoupe vertical
		int midx = 256 / 2;
		int midy = 256 / 2;
		int halfcut = 256 / 4;
		while (it.getValue().state == 2) {
			if (cuthrzn) {
				if ((y1 < midy & y2 < midy) || (y1 >= midy & y2 >= midy)) {
					if (y1 < midy) {
						it.goLeft();
						midy -= halfcut;
					} else {
						it.goRight();
						midy += halfcut;
					}
				} else
					return false;
			} else {
				if ((x1 < midx & x2 < midx) || (x1 >= midx & x2 >= midx)) {
					if (x1 < midx) {
						it.goLeft();
						midx -= halfcut;
					} else {
						it.goRight();
						midx += halfcut;
					}
					halfcut = halfcut / 2;
				} else
					return false;
			}
			cuthrzn = !cuthrzn;
		}
		return true;
	}

	/**
	 * @param image2 autre image
	 * @pre !this.isEmpty() && !image2.isEmpty()
	 * @return true si this est incluse dans image2 au sens des pixels allumés
	 *         false sinon
	 */
	@Override
	public boolean isIncludedIn(AbstractImage image2) {
		Iterator<Node> it1 = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		return isIncludedIn(it1, it2);
	}

	private boolean isIncludedIn(Iterator<Node> it1, Iterator<Node> it2) {
		int s1 = it1.getValue().state;
		int s2 = it2.getValue().state;
		boolean res = true;
		switch (s1) {
		case 0:
			return true;
		case 1:
			return s2 == 1;
		case 2:
			if (s2 == 2) {
				it1.goLeft();
				it2.goLeft();
				res = res && isIncludedIn(it1, it2);
				it1.goUp();
				it2.goUp();
				it2.goRight();
				it1.goRight();
				res = res && isIncludedIn(it1, it2);
				it1.goUp();
				it2.goUp();
				return res;
			} else
				return s2 == 1;
		}
		return res;
	}

//  IS SUB TREE
//	private boolean isIncludedIn(Iterator<Node> it1, Iterator<Node> it2) {
//		boolean res = isEqual(it1, it2);
//		if (!it2.isEmpty()) {
//			if (!res) {
//				it2.goLeft();
//				res = isIncludedIn(it1, it2);
//				it2.goUp();
//			}
//			if (!res) {
//				it2.goRight();
//				res = isIncludedIn(it1, it2);
//				it2.goUp();
//			}
//		}
//		return res;
//	}
//
//	private boolean isEqual(Iterator<Node> it1, Iterator<Node> it2) {
//		if (it1.isEmpty() && it2.isEmpty())
//			return true;
//		if (it1.isEmpty() ^ it2.isEmpty())
//			return false;
//		if (it1.getValue().state == it2.getValue().state) {
//			System.out.println(it1.getValue().state + "-" + it2.getValue().state);
//			it1.goLeft();
//			it2.goLeft();
//			boolean left = isEqual(it1, it2);
//			it1.goUp();
//			it2.goUp();
//			it1.goRight();
//			it2.goRight();
//			boolean right = isEqual(it1, it2);
//			it1.goUp();
//			it2.goUp();
//			return (left && right);
//		}
//		return false;
//	}
}
