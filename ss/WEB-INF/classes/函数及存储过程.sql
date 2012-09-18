
/**函数及存储过程***/
CREATE OR REPLACE FUNCTION MinutesBeforeTime(oldTime Date,minutes NUMBER) RETURN DATE IS
/******************************************************************************
   NAME:       MINUTESBEFORE
   PURPOSE:    获取指定时间n分钟前的时间
******************************************************************************/
BEGIN
   RETURN oldTime-((1 * minutes) / 1440);
END MinutesBeforeTime;
/
CREATE OR REPLACE FUNCTION MinutesBeforeNow(minutes NUMBER) RETURN DATE IS
/******************************************************************************
   NAME:       MINUTESBEFORE
   PURPOSE:    获取当前系统时间n分钟前的时间
******************************************************************************/
BEGIN
   RETURN MinutesBeforeTime(SYSDATE,minutes);
END MinutesBeforeNow;
/
CREATE OR REPLACE FUNCTION HoursBeforeTime(oldTime Date,hours NUMBER) RETURN DATE IS
/******************************************************************************
   NAME:       HoursBefore
   PURPOSE:    获取指定时间n小时前的时间
******************************************************************************/
BEGIN
   RETURN oldTime-((1 * hours) / 24);
END HoursBeforeTime;
/

CREATE OR REPLACE FUNCTION MinutesBefore(minutes NUMBER) RETURN DATE IS
/******************************************************************************
   NAME:       MINUTESBEFORE
   PURPOSE:    获取当前系统时间n分钟前的时间
******************************************************************************/
BEGIN
   RETURN MinutesBeforeTime(SYSDATE,minutes);
END MinutesBefore;
/
CREATE OR REPLACE FUNCTION HoursBeforeNow(hours NUMBER) RETURN DATE IS
/******************************************************************************
   NAME:       HoursBefore
   PURPOSE:    获取当前系统时间n小时前的时间
******************************************************************************/
BEGIN
   RETURN HoursBeforeTime(SYSDATE,hours);
END HoursBeforeNow;
/
CREATE OR REPLACE FUNCTION HoursBefore(hours NUMBER) RETURN DATE IS
/******************************************************************************
   NAME:       HoursBefore
   PURPOSE:    获取当前系统时间n小时前的时间
******************************************************************************/
BEGIN
   RETURN HoursBeforeTime(SYSDATE,hours);
END HoursBefore;
/

CREATE OR REPLACE PROCEDURE manactive (
   keyid   IN   VARCHAR2,
   gps     IN   VARCHAR2,
   op      IN   VARCHAR2
)
IS
   n   NUMBER;
/******************************************************************************
   NAME:       ManActive
   PURPOSE:    简化收到短信后更新ONLINEMAN表的问题

   REVISIONS:
   Ver        Date        Author           Description
   ---------  ----------  ---------------  ------------------------------------
   1.0        2006-3-19   YuLeyuan         1. 更新在线巡检员表.

   NOTES:
      涉及到的表:  ONLINEMAN
      参数      :  keyid  SIMID号
                   gps    GPS座标
                   op     操作标志  1 巡检 2 制定线路 3 盯防
******************************************************************************/
BEGIN
   --首先看表中是否已有该simid的记录
   SELECT COUNT (simid)
     INTO n
     FROM onlineman
    WHERE simid = keyid;
   --如果记录已存在，则更新该条记录，否则插入一条记录
   IF ((n IS NOT NULL) AND (n > 0))
   THEN
      UPDATE onlineman
         SET coordinate = gps,
             operate = op,
             activetime = SYSDATE
       WHERE simid = keyid;
   ELSE
      INSERT INTO onlineman
                  (simid, coordinate, operate, activetime
                  )
           VALUES (keyid, gps, op, SYSDATE
                  );
   END IF;
END manactive;
/
