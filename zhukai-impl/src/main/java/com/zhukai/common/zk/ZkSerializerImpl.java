package com.zhukai.common.zk;

import java.io.UnsupportedEncodingException;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.I0Itec.zkclient.serialize.ZkSerializer;

/**
 * 现只用zk存储String做简单使用, 因此序列化和反序列化在String中转换
 * 
 * zkclient中已有两种序列化方式
 * @see BytesPushThroughSerializer
 * @see SerializableSerializer
 * 
 * @author Juke
 */
public class ZkSerializerImpl implements ZkSerializer {

	@Override
	public byte[] serialize(Object data) throws ZkMarshallingError {
		return data.toString().getBytes();
	}

	@Override
	public Object deserialize(byte[] bytes) throws ZkMarshallingError {
		try {
			return new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new ZkMarshallingError(e);
		}
	}

}
