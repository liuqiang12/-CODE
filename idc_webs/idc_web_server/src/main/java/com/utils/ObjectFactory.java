package com.utils;

import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * Created by DELL on 2017/8/16.
 */
@XmlRegistry
public class ObjectFactory
{
    private final static QName _Configurations_SCALERULE_QNAME = new QName(
            "http://www.huawei.com/cloudCube/cloudview", "entities");

    public ObjectFactory()
    {
    }
    public FileLoad createFileLoad()
    {
        return new FileLoad();
    }
}
