package com.javaweb.smartUpload;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;

public class Files {
	private SmartUpload m_parent;
	private Hashtable m_files = new Hashtable();
	private int m_counter = 0;

	protected void addFile(File arg0) {
		if (arg0 == null) {
			throw new IllegalArgumentException("newFile cannot be null.");
		} else {
			this.m_files.put(new Integer(this.m_counter), arg0);
			++this.m_counter;
		}
	}

	public File getFile(int arg0) {
		if (arg0 < 0) {
			throw new IllegalArgumentException("File\'s index cannot be a negative value (1210).");
		} else {
			File arg1 = (File) this.m_files.get(new Integer(arg0));
			if (arg1 == null) {
				throw new IllegalArgumentException("Files\' name is invalid or does not exist (1205).");
			} else {
				return arg1;
			}
		}
	}

	public int getCount() {
		return this.m_counter;
	}

	public long getSize() throws IOException {
		long arg0 = 0L;

		for (int arg2 = 0; arg2 < this.m_counter; ++arg2) {
			arg0 += (long) this.getFile(arg2).getSize();
		}

		return arg0;
	}

	public Collection getCollection() {
		return this.m_files.values();
	}

	public Enumeration getEnumeration() {
		return this.m_files.elements();
	}
}
