package com.cabletech.uploadfile;

import java.text.MessageFormat;

public class FileSequence {
	private static int seq;
	private static MessageFormat fmt = new MessageFormat("{0,number,000}");

	public static synchronized String getSeq() {
		seq++;
		if (seq > 999) {
			seq = 0;
		}
		Object[] param = { new Integer(seq) };
		return fmt.format(param);
	}

	public static void main(String[] args) {
		System.out.println(FileSequence.getSeq());
	}

}
