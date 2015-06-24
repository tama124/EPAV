package tama.edu.java.model;

import java.util.ArrayList;

public class PatchList {
	private ArrayList<Patch> listOfPatch;

	public ArrayList<Patch> getListOfPatch() {
		return listOfPatch;
	}

	public PatchList() {
		listOfPatch = new ArrayList<Patch>();
	}

	public void add(Patch patch) {
		listOfPatch.add(patch);
	}

	public String display() {
		String tmp = new String();
		for (int i = 0; i < this.listOfPatch.size(); i++) {
			tmp += this.listOfPatch.get(i);
			if (i + 1 < this.listOfPatch.size()) {
				tmp += ", ";
			}
		}
		return tmp;
	}
}
