package com.cabletech.baseinfo.services;

import java.sql.*;
import java.util.*;

import org.apache.log4j.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.sqlbuild.QuerySqlBuild;

public class CableSegmentBO extends BaseBisinessObject{
    private static Logger logger = Logger.getLogger( "CableSegmentBO" );

    public CableSegmentBO(){
    }


    public List queryCableSegment( CableSegment data ){
        String sql = "select kid,segmentid,segmentname,segmentdesc,pointa,pointz," +
        		"route,laytype,corenumber,owner,producer,builder,cabletype,finishtime,fibertype,remark from repeatersection";
        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );
        sqlBuild.addConditionAnd( "segmentid like {0}", "%" + data.getSegmentid() + "%" );
        sqlBuild.addConditionAnd( "segmentname like {0}", "%" + data.getSegmentname() + "%" );
        sql = sqlBuild.toSql();
        logger.info("sql:"+sql);

        try{
            QueryUtil query = new QueryUtil();
            return query.queryBeans( sql );
        }
        catch( Exception ex ){
            logger.error( "查询光缆信息异常：" + ex.getMessage() );
            return null;
        }

    }


    public boolean addCableSegment( CableSegment data ){
        String sql = "insert into repeatersection(kid,segmentid,segmentname,segmentdesc,pointa,pointz," +
        		"route,laytype,grosslength,corenumber,owner,producer,builder,cabletype," +
        		"finishtime,fibertype,reservedlength,remark) values('"
                     + data.getKid() + "','" + data.getSegmentid() + "','" + data.getSegmentname() + "','"
                     + data.getSegmentdesc() + "','" + data.getPointa() + "','" + data.getPointz() + "','"
                     + data.getRoute() + "','" + data.getLaytype() + "'," + data.getGrosslength() + ",'"
                     + data.getCorenumber() + "','" + data.getOwner() + "','" + data.getProducer() + "','"
                     + data.getBuilder() + "','" + data.getCabletype() + "','" + data.getFinishtime() + "','"
                     + data.getFibertype() + "'," + data.getReservedlength() + ",'" + data.getRemark() + "')";
        UpdateUtil update = null;
        try{
            logger.info( "sql -> " + sql );
            update = new UpdateUtil();
            update.executeUpdate( sql );
            return true;
        }
        catch( Exception ex ){
            logger.error( "增加光缆信息异常：" + ex.getMessage() );
            return false;
        }

    }


    /**
     * getCableSegment
     *
     * @param kid String
     * @return CableSegmentBO
     */
    public CableSegment getCableSegment( String kid ){
        String sql = "select kid,segmentid,segmentname,segmentdesc,pointa,pointz,route,laytype," +
        		"grosslength,corenumber,owner,producer,builder,cabletype,finishtime,fibertype," +
        		"reservedlength,remark from repeatersection where kid='"
                     + kid + "'";
        CableSegment data = new CableSegment();
        try{
            ResultSet rs = null;
            QueryUtil query = new QueryUtil();
            rs = query.executeQuery( sql );
            rs.next();
            data.setKid( rs.getString( "kid" ) );
            data.setSegmentid( rs.getString( "segmentid" ) );
            data.setSegmentname( rs.getString( "segmentname" ) );
            data.setSegmentdesc( rs.getString( "segmentdesc" ) );
            data.setPointa( rs.getString( "pointa" ) );
            data.setPointz( rs.getString( "pointz" ) );
            data.setRoute( rs.getString( "route" ) );
            data.setLaytype( rs.getString( "laytype" ) );
            data.setGrosslength( rs.getBigDecimal( "grosslength" ) );
            data.setCorenumber( rs.getInt( "corenumber" ) );
            data.setOwner( rs.getString( "owner" ) );
            data.setProducer( rs.getString( "producer" ) );
            data.setBuilder( rs.getString( "builder" ) );
            data.setCabletype( rs.getString( "cabletype" ) );
            data.setFinishtime( rs.getString( "finishtime" ) );
            data.setFibertype( rs.getString( "fibertype" ) );
            data.setReservedlength( rs.getBigDecimal( "reservedlength" ) );
            data.setRemark( rs.getString( "remark" ) );
            return data;
        }
        catch( Exception ex ){
            logger.error( "获取光缆信息时异常：" + ex.getMessage() );
            return null;
        }

    }


    /**
     * updateCableSegment
     *
     * @param csdata CableSegment
     * @return boolean
     */
    public boolean updateCableSegment( CableSegment csdata ){
        String sql = "update repeatersection set segmentid = '" + csdata.getSegmentid() + "',segmentname ='" + csdata.getSegmentname() + "',segmentdesc='"+ csdata.getSegmentdesc() + "',pointa='" + csdata.getPointa() + "',pointz='" + csdata.getPointz() + "',route='"+ csdata.getRoute() + "',laytype='" + csdata.getLaytype() + "',grosslength=" + csdata.getGrosslength() + ",corenumber='"+ csdata.getCorenumber() + "',owner='" + csdata.getOwner() + "',producer='" + csdata.getProducer() + "',builder='"+ csdata.getBuilder() + "',cabletype='" + csdata.getCabletype() + "',finishtime='" + csdata.getFinishtime() + "',fibertype='"+ csdata.getFibertype() + "',reservedlength=" + csdata.getReservedlength() + ",remark='" + csdata.getRemark() + "' where kid='"+csdata.getKid()+"'";

        UpdateUtil update = null;
        try{
            logger.info( "sql -> " + sql );
            update = new UpdateUtil();
            update.executeUpdate( sql );
            return true;
        }
        catch( Exception ex ){
            logger.error( "更新光缆信息异常：" + ex.getMessage() );
            return false;
        }
    }


    /**
     * delCableSegment
     *
     * @param kid String
     * @return boolean
     */
    public boolean delCableSegment( String kid ){
        String sql = "delete repeatersection where kid='"+kid+"'";

       UpdateUtil update = null;
       try{
           logger.info( "sql -> " + sql );
           update = new UpdateUtil();
           update.executeUpdate( sql );
           return true;
       }
       catch( Exception ex ){
           logger.error( "删除光缆信息异常：" + ex.getMessage() );
           return false;
       }

    }
}
