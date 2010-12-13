 /*
 * Copyright (C) 2010 Google Code.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.resting.helper;

import static com.google.resting.helper.DeleteHelper.delete;
import static com.google.resting.helper.GetHelper.get;
import static com.google.resting.helper.PostHelper.post;
import static com.google.resting.helper.PutHelper.put;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.resting.component.Alias;
import com.google.resting.component.RequestParams;
import com.google.resting.component.Verb;
import com.google.resting.component.impl.JSONAlias;
import com.google.resting.component.impl.ServiceResponse;
import com.google.resting.transform.TransformationType;
import com.google.resting.transform.impl.JSONTransformer;
import com.google.resting.transform.impl.XMLTransformer;
/**
 * Helper class for Resting.
 * 
 * @author sujata.de
 * @since resting 0.1
 *
 */

public final class RestingHelper {

	public final static<T> List<T> executeAndTransform(String url, int port, RequestParams requestParams, Verb verb, TransformationType transformationType, Class<T> targetType, Alias alias){
		ServiceResponse serviceResponse=null;
		if(verb==Verb.GET)
			serviceResponse=get(url, port,requestParams);
		else if (verb == Verb.DELETE)
			serviceResponse=delete(url, port,requestParams);
		else if (verb==Verb.POST)
			serviceResponse=post(url, port,requestParams);
		else if (verb==Verb.PUT)
			serviceResponse=put(url, port,requestParams);
		
		List<T> results=new ArrayList<T>();
		if(transformationType==TransformationType.JSON){
			JSONTransformer<T> transformer=new JSONTransformer<T>();
			results=transformer.getEntityList(serviceResponse, targetType, alias);
		}//JSON
		
		if(transformationType==TransformationType.XML){
			XMLTransformer<T> transformer=new XMLTransformer<T>();
			results=transformer.getEntityList(serviceResponse, targetType, alias);
			
		}//XML
		
		return results;
	}//executeAndTransform
	
	public final static Map<String, List> executeAndTransform(String url, int port, RequestParams requestParams, Verb verb, TransformationType transformationType, JSONAlias alias){
		ServiceResponse serviceResponse=null;
		if(verb==Verb.GET)
			serviceResponse=get(url, port,requestParams);
		else if (verb == Verb.DELETE)
			serviceResponse=delete(url, port,requestParams);
		else if (verb==Verb.POST)
			serviceResponse=post(url, port,requestParams);
		else if (verb==Verb.PUT)
			serviceResponse=put(url, port,requestParams);
		
		Map<String, List> results=null;
		
		if(transformationType==TransformationType.JSON){
			JSONTransformer<Object> transformer=new JSONTransformer<Object>();
			results=transformer.getEntityLists(serviceResponse,  alias);
		}//JSON
		
		//Not needed for XML
		
		
		return results;
	}//executeAndTransform
}