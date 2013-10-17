package com.roy.tiny.base.dao;

import java.util.HashMap;
import java.util.Map;

import com.roy.tiny.base.dao.cond.Cond;
import com.roy.tiny.base.dao.cond.logic.LogicCond;
import com.roy.tiny.base.dao.cond.logic.NotCond;
import com.roy.tiny.base.dao.cond.property.PropertyCond;
import com.roy.tiny.base.web.Sort;
import com.roy.tiny.base.web.Sorter;

/**
 * Deal with hierarchy alias.
 * @author Roy Wang 
 *
 */
public final class AliasHelper {

	private AliasHelper() {}

	public final static Map<String,String> getAliasMap(final Cond cond) {
		Map<String,String> aliasMap = new HashMap<String,String>();
		if(cond instanceof NotCond) {
			NotCond cnd = (NotCond) cond;
			if(cnd.getCond()!=null) {
				aliasMap.putAll(getAliasMap(cnd.getCond()));
			}
		} else if(cond instanceof PropertyCond) {
			PropertyCond cnd = (PropertyCond) cond;
			if(cnd.getProperty()!=null && cnd.getProperty().indexOf(".") > -1) {
				addAliasToMap(aliasMap,cnd.getProperty());
				cnd.setProperty(getRenameAlias(cnd.getProperty()));
			}
		} else if(cond instanceof LogicCond) {
			LogicCond cnd = (LogicCond) cond;
			if(cnd.getLeft()!=null) {
				aliasMap.putAll(getAliasMap(cnd.getLeft()));
			}
			if(cnd.getRight()!=null) {
				aliasMap.putAll(getAliasMap(cnd.getRight()));
			}
		}
		return aliasMap;
	}
	
	public final static Map<String,String> getAliasMap(final Sorter sorter) {
		Map<String,String> aliasMap = new HashMap<String,String>();
		for(Sort sort : sorter) {
			if(sort.getName()!=null && sort.getName().indexOf(".") > -1) {
				addAliasToMap(aliasMap,sort.getName());
				sort.setName(getRenameAlias(sort.getName()));
			}
		}
		return aliasMap;
	}

	private static void addAliasToMap(Map<String,String> aliasMap,final String property) {
		if(property==null || property.indexOf(".") == -1) {
			return;
		}
		String rightPath = property.substring(property.indexOf(".")+1);
		while(true) {
			String alias = property.substring(0, property.lastIndexOf(rightPath)-1);
			aliasMap.put(getRenameAlias(alias), alias.replace(".", "_"));
			int index = rightPath.indexOf(".");
			if(index==-1) {
				break;
			}
			else {
				rightPath = rightPath.substring(index+1);
			}
		}
	}

	private static String getRenameAlias(final String property) {
		int lastIndex = property.lastIndexOf(".");
		if(property.indexOf(".")==lastIndex) {
			return property;
		}
		else {
			return property.substring(0, lastIndex).replace(".", "_")+property.substring(lastIndex);
		}
	}

}
