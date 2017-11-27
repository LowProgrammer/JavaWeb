package com.javaweb.smartUpload;
import java.util.Enumeration;
import java.util.Hashtable;

public class Request {
	private Hashtable m_parameters = new Hashtable();
	private int m_counter = 0;

	protected void putParameter(String arg0, String arg1) {
		if (arg0 == null) {
			throw new IllegalArgumentException("The name of an element cannot be null.");
		} else {
			Hashtable arg2;
			if (this.m_parameters.containsKey(arg0)) {
				arg2 = (Hashtable) this.m_parameters.get(arg0);
				arg2.put(new Integer(arg2.size()), arg1);
			} else {
				arg2 = new Hashtable();
				arg2.put(new Integer(0), arg1);
				this.m_parameters.put(arg0, arg2);
				++this.m_counter;
			}

		}
	}

	public String getParameter(String arg0) {
		if (arg0 == null) {
			throw new IllegalArgumentException("Form\'s name is invalid or does not exist (1305).");
		} else {
			Hashtable arg1 = (Hashtable) this.m_parameters.get(arg0);
			return arg1 == null ? null : (String) arg1.get(new Integer(0));
		}
	}

	public Enumeration getParameterNames() {
		return this.m_parameters.keys();
	}

	public String[] getParameterValues(String arg0) {
		if (arg0 == null) {
			throw new IllegalArgumentException("Form\'s name is invalid or does not exist (1305).");
		} else {
			Hashtable arg1 = (Hashtable) this.m_parameters.get(arg0);
			if (arg1 == null) {
				return null;
			} else {
				String[] arg2 = new String[arg1.size()];

				for (int arg3 = 0; arg3 < arg1.size(); ++arg3) {
					arg2[arg3] = (String) arg1.get(new Integer(arg3));
				}

				return arg2;
			}
		}
	}
}
