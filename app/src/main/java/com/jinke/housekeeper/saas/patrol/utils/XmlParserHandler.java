package com.jinke.housekeeper.saas.patrol.utils;


import com.jinke.housekeeper.saas.patrol.bean.ApWheelViewModel;
import com.jinke.housekeeper.saas.patrol.bean.HourWheelViewModel;
import com.jinke.housekeeper.saas.patrol.bean.MinuteWheelViewModel;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class XmlParserHandler extends DefaultHandler {

    /**
     * 存储所有的解析对象
     */
    private List<ApWheelViewModel> apList = new ArrayList<ApWheelViewModel>();

    ApWheelViewModel apModel = new ApWheelViewModel();
    HourWheelViewModel hourModel = new HourWheelViewModel();
    MinuteWheelViewModel minuteModel = new MinuteWheelViewModel();

    public XmlParserHandler() {

    }

    public List<ApWheelViewModel> getDataList() {
        return apList;
    }

    @Override
    public void startDocument() throws SAXException {
        // 当读到第一个开始标签的时候，会触发这个方法
    }



    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        // 当遇到开始标记的时候，调用这个方法
        if (qName.equals("ap")) {
            apModel = new ApWheelViewModel();
            apModel.setName(attributes.getValue(0));
            apModel.setHourList(new ArrayList<HourWheelViewModel>());
        } else if (qName.equals("hour")) {
            hourModel = new HourWheelViewModel();
            hourModel.setName(attributes.getValue(0));
            hourModel.setMinuteList(new ArrayList<MinuteWheelViewModel>());
        } else if (qName.equals("minutes")) {
            minuteModel = new MinuteWheelViewModel();
            minuteModel.setName(attributes.getValue(0));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        // 遇到结束标记的时候，会调用这个方法
        if (qName.equals("ap")) {
            apList.add(apModel);
        } else if (qName.equals("hour")) {
            apModel.getHourList().add(hourModel);
        } else if (qName.equals("minutes")) {
            hourModel.getMinuteList().add(minuteModel);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
    }

}
