package com.cabletech.linechange.bean;

import com.cabletech.commons.base.BaseBean;
import com.cabletech.commons.util.DateUtil;

public class ChangeCheckBean extends BaseBean{
    private java.lang.String changeid;
   private String checkdate;
   private String checkdatum;
   private java.lang.String checkperson;
   private String checkremark;
   private String checkresult;
   private String fillinperson;
   private String fillintime;
   private java.lang.String id;
    private String begintime;
    private String endtime;
    private String changename;
    private String changepro;
    private String lineclass;
    private String step;
    private String changeaddr;
    private Float square;

    public ChangeCheckBean(){
           super();
           checkdate = DateUtil.getNowDateString( "yyyy/MM/dd" );
           fillintime = DateUtil.getNowDateString( "yyyy/MM/dd" );
       }

   /**
    * Return the value associated with the column: CHANGEID
    */
   public java.lang.String getChangeid(){
       return changeid;
   }


   /**
    * Set the value related to the column: CHANGEID
    * @param changeid the CHANGEID value
    */
   public void setChangeid( java.lang.String changeid ){
       this.changeid = changeid;
   }


   /**
    * Return the value associated with the column: checkdate
    */
   public String getCheckdate(){
       return checkdate;
   }


   /**
    * Set the value related to the column: checkdate
    * @param checkdate the checkdate value
    */
   public void setCheckdate( String checkdate ){
       this.checkdate = checkdate;
   }


   /**
    * Return the value associated with the column: checkdatum
    */
   public String getCheckdatum(){
       return checkdatum;
   }


   /**
    * Set the value related to the column: checkdatum
    * @param checkdatum the checkdatum value
    */
   public void setCheckdatum( String checkdatum ){
       this.checkdatum = checkdatum;
   }


   /**
    * Return the value associated with the column: checkperson
    */
   public java.lang.String getCheckperson(){
       return checkperson;
   }


   /**
    * Set the value related to the column: checkperson
    * @param checkperson the checkperson value
    */
   public void setCheckperson( java.lang.String checkperson ){
       this.checkperson = checkperson;
   }


   /**
    * Return the value associated with the column: checkremark
    */
   public String getCheckremark(){
       return checkremark;
   }


   /**
    * Set the value related to the column: checkremark
    * @param checkremark the checkremark value
    */
   public void setCheckremark( String checkremark ){
       this.checkremark = checkremark;
   }


   /**
    * Return the value associated with the column: checkresult
    */
   public String getCheckresult(){
       return checkresult;
   }


   /**
    * Set the value related to the column: checkresult
    * @param checkresult the checkresult value
    */
   public void setCheckresult( String checkresult ){
       this.checkresult = checkresult;
   }


   /**
    * Return the value associated with the column: fillinperson
    */
   public String getFillinperson(){
       return fillinperson;
   }


   /**
    * Set the value related to the column: fillinperson
    * @param fillinperson the fillinperson value
    */
   public void setFillinperson( String fillinperson ){
       this.fillinperson = fillinperson;
   }


   /**
    * Return the value associated with the column: fillintime
    */
   public String getFillintime(){
       return fillintime;
   }


   /**
    * Set the value related to the column: fillintime
    * @param fillintime the fillintime value
    */
   public void setFillintime(String fillintime ){
       this.fillintime = fillintime;
   }


   /**
    * Return the value associated with the column: ID
    */
   public java.lang.String getId(){
       return id;
   }


    public String getBegintime(){
        return begintime;
    }


    public String getEndtime(){
        return endtime;
    }


    public String getChangename(){
        return changename;
    }


    public String getChangepro(){
        return changepro;
    }


    public String getLineclass(){
        return lineclass;
    }


    public String getStep(){
        return step;
    }


    public String getChangeaddr(){
        return changeaddr;
    }


    /**
    * Set the value related to the column: ID
    * @param id the ID value
    */
   public void setId( java.lang.String id ){
       this.id = id;
   }


    public void setBegintime( String begintime ){
        this.begintime = begintime;
    }


    public void setEndtime( String endtime ){
        this.endtime = endtime;
    }


    public void setChangename( String changename ){
        this.changename = changename;
    }


    public void setChangepro( String changepro ){
        this.changepro = changepro;
    }


    public void setLineclass( String lineclass ){
        this.lineclass = lineclass;
    }


    public void setStep( String step ){
        this.step = step;
    }


    public void setChangeaddr( String changeaddr ){
        this.changeaddr = changeaddr;
    }

    public Float getSquare() {
        return square;
    }

    public void setSquare(Float square) {
        this.square = square;
    }

}
