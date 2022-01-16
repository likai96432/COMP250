import java.util.ArrayList;
import java.util.Iterator;

import java.util.NoSuchElementException;

public class CatTree implements Iterable<CatInfo> {
	public CatNode root;

	public CatTree(CatInfo c) {
		this.root = new CatNode(c);
	}

	private CatTree(CatNode c) {
		this.root = c;
	}

	public void addCat(CatInfo c) {
		this.root = root.addCat(new CatNode(c));
	}

	public void removeCat(CatInfo c) {
		this.root = root.removeCat(c);
	}

	public int mostSenior() {
		return root.mostSenior();
	}

	public int fluffiest() {
		return root.fluffiest();
	}

	public CatInfo fluffiestFromMonth(int month) {
		return root.fluffiestFromMonth(month);
	}

	public int hiredFromMonths(int monthMin, int monthMax) {
		return root.hiredFromMonths(monthMin, monthMax);
	}

	public int[] costPlanning(int nbMonths) {
		return root.costPlanning(nbMonths);
	}

	public Iterator<CatInfo> iterator() {
		return new CatTreeIterator();
	}

	class CatNode {

		CatInfo data;
		CatNode senior;
		CatNode same;
		CatNode junior;

		public CatNode(CatInfo data) {
			this.data = data;
			this.senior = null;
			this.same = null;
			this.junior = null;
		}

		public String toString() {
			String result = this.data.toString() + "\n";
			if (this.senior != null) {
				result += "more senior " + this.data.toString() + " :\n";
				result += this.senior.toString();
			}
			if (this.same != null) {
				result += "same seniority " + this.data.toString() + " :\n";
				result += this.same.toString();
			}
			if (this.junior != null) {
				result += "more junior " + this.data.toString() + " :\n";
				result += this.junior.toString();
			}
			return result;
		}

		public CatNode addCat(CatNode c) {

			if (root == null) {
				root = c;
			}

			if (this.data.monthHired > c.data.monthHired) {
				if (this.senior != null)
					this.senior.addCat(c);
				else
					this.senior = c;
			}
			if (this.data.monthHired < c.data.monthHired) {
				if (this.junior != null)
					this.junior.addCat(c);
				else
					this.junior = c;
			}
			if (this.data.monthHired == c.data.monthHired) {
				if (this.data.furThickness < c.data.furThickness) {
					CatNode current = new CatNode(this.data);
					this.data = c.data;
					c.data = current.data;
					this.addCat(c);
				} else {
					if (this.same == null)
						this.same = c;
					else
						this.same.addCat(c);
				}
			}

			return root;
		}

		public CatNode removeCat(CatInfo c) {

			CatNode current = root;
			CatNode prev = null;
			CatNode x = null;
			CatNode y = null;
			while (current.data.monthHired > c.monthHired) {
				prev = current;
				current = current.senior;
				if (current == null)
					return root;

			}
			while (current.data.monthHired < c.monthHired) {
				prev = current;
				current = current.junior;
				if (current == null)
					return root;

			}
			while (!current.data.equals(c)) {
				prev = current;
				current = current.same;
				if (current == null)
					return root;
			}
			if (current.same != null) {
				if (root.data.monthHired == current.data.monthHired) {
					root = current.same;
				} else if (root.data.monthHired > current.data.monthHired) {
					prev.senior = current.same;
				} else {
					prev.junior = current.same;
				}

				current.same.junior = current.junior;
				current.same.senior = current.senior;
				current.junior = null;
				current.senior = null;
				current.same = null;

				return root;
			} else if (current.senior != null) {
				if (root.data.monthHired == current.data.monthHired) {
					root = current.senior;
				} else if (root.data.monthHired > current.data.monthHired) {
					prev.senior = current.senior;
				} else {
					prev.junior = current.junior;
				}

				x = current.senior;
				y = x;
				while (x.junior != null) {
					y = x;
					x = x.junior;
				}
				y.junior = current.junior;
				current.junior = null;
				current.senior = null;
				return root;
			} else {
				if (root.data.monthHired == current.data.monthHired) {
					root = current.junior;
				} else if (root.data.monthHired > current.data.monthHired) {
					prev.senior = current.junior;
				} else {
					prev.junior = current.junior;
				}
				current.junior = null;
				current.senior = null;
				return root;

			}
			
		}

		public int mostSenior() {

			if (this.senior == null) {
				return this.data.monthHired;
			}
			this.senior.mostSenior();
			return 0;

		}

		public int fluffiest() {
			return fluff(this);

		}

		private int fluff(CatNode node) {
			if (node == null) {
				return 0;
			}
			int n = node.data.furThickness;
			int juniormax = 0;
			int seniormax = 0;

			juniormax = fluff(node.junior);
			n = Math.max(n, juniormax);
			seniormax = fluff(node.senior);
			n = Math.max(n, seniormax);
			return n;
		}

		public int hiredFromMonths(int monthMin, int monthMax) {
			int count = 1;

			if (this.data.monthHired < monthMin && this.junior == null) {
				return 0;

			} else if (this.data.monthHired < monthMin && this.junior != null) {

				count = this.junior.hiredFromMonths(monthMin, monthMax);

			}

			else if (this.data.monthHired > monthMax && this.senior == null) {
				return 0;

			} else if (this.data.monthHired > monthMax && this.senior != null) {

				count = this.senior.hiredFromMonths(monthMin, monthMax);

			} else if (monthMin > monthMax) {
				return 0;
			} else if (this.data.monthHired >= monthMin && this.data.monthHired <= monthMax) {
				if (this.same != null) {
					count = count + this.same.hiredFromMonths(monthMin, monthMax);

				}

				if (this.junior != null) {
					count = count + this.junior.hiredFromMonths(monthMin, monthMax);
				}

				if (this.senior != null) {
					count = count + this.senior.hiredFromMonths(monthMin, monthMax);
				}

			}

			return count;

		}

		public CatInfo fluffiestFromMonth(int month) {
			if (root == null) {
				return null;
			} else
				return root.data;

		}

		public int[] costPlanning(int nbMonths) {
			int month = 243;
			int[] costarray = new int[nbMonths];

			for (int i = 0; i < nbMonths; i++) {
				int cost = 0;
				cost = this.costatmonth(month);
				month++;
				costarray[i] = cost;
			}
			return costarray; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
		}

		private int costatmonth(int month) {
			if (root == null)
				return 0;
			int cost = 0;
			if (this.data.nextGroomingAppointment == month) {
				cost += this.data.expectedGroomingCost;
			}
			if (this.same != null) {
				cost += this.same.costatmonth(month);
			}
			if (this.senior != null) {
				cost += this.senior.costatmonth(month);
			}
			if (this.junior != null) {
				cost += this.junior.costatmonth(month);
			}

			return cost;
		}
	}

	 private class CatTreeIterator implements Iterator<CatInfo> {
	        // HERE YOU CAN ADD THE FIELDS YOU NEED
	        
	        public CatTreeIterator() {
	            //YOUR CODE GOES HERE
	        }
	        
	        public CatInfo next(){
	            //YOUR CODE GOES HERE
	            return null; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
	        }
	        
	        public boolean hasNext() {
	            //YOUR CODE GOES HERE
	            return false; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
	        }
	    }


}
