package com.codejohnny.enums;


public enum CjSqlDatatype {
	SMALLINT,
	BIGINT,
	NUMERIC,
	SERIAL,
	DOUBLE,
	FLOAT,
	FLOAT4,
	FLOAT8,
	INT,
	INT4,
	CHAR,
	DATE,
	DATETIME,
	LONGTEXT,
	MEDIUMTEXT,
	TEXT,
	TIME,
	TIMESTAMP,
	VARCHAR,
	TINYINT,
	BIT,
	BOOL,
	NA;
	
	public static CjSqlDatatype toSqlDataType(String Str)
	{
		try {return valueOf(Str);}
		catch (Exception ex){return NA;}
	}
}

