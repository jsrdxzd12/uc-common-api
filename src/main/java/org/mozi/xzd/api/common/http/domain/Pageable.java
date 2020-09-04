package org.mozi.xzd.api.common.http.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Pageable implements Serializable {

   // @JsonIgnore
	private long current = 1;//当前页数
  //@JsonIgnore
	private long size = 20;//当页显示行数


}