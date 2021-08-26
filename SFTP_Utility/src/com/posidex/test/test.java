package com.posidex.test;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;

import com.posidex.customanttasks.StringEncrypter;

public class test {
public static void main(String[] args) throws DecoderException, EncoderException {
	System.out.println(StringEncrypter.decrypt("tttjJtbEL/DnEAxgelzkvA=="));
	
	System.out.println(StringEncrypter.decrypt("K3uVUJ3I9i7q408aDAGfQ=="));
	
	System.out.println(StringEncrypter.encrypt("Admin@321"));
}
}
