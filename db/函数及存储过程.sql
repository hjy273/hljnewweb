
/**�������洢����***/
CREATE OR REPLACE FUNCTION MinutesBeforeTime(oldTime Date,minutes NUMBER) RETURN DATE IS
/******************************************************************************
   NAME:       MINUTESBEFORE
   PURPOSE:    ��ȡָ��ʱ��n����ǰ��ʱ��
******************************************************************************/
BEGIN
   RETURN oldTime-((1 * minutes) / 1440);
END MinutesBeforeTime;
/
CREATE OR REPLACE FUNCTION MinutesBeforeNow(minutes NUMBER) RETURN DATE IS
/******************************************************************************
   NAME:       MINUTESBEFORE
   PURPOSE:    ��ȡ��ǰϵͳʱ��n����ǰ��ʱ��
******************************************************************************/
BEGIN
   RETURN MinutesBeforeTime(SYSDATE,minutes);
END MinutesBeforeNow;
/
CREATE OR REPLACE FUNCTION HoursBeforeTime(oldTime Date,hours NUMBER) RETURN DATE IS
/******************************************************************************
   NAME:       HoursBefore
   PURPOSE:    ��ȡָ��ʱ��nСʱǰ��ʱ��
******************************************************************************/
BEGIN
   RETURN oldTime-((1 * hours) / 24);
END HoursBeforeTime;
/

CREATE OR REPLACE FUNCTION MinutesBefore(minutes NUMBER) RETURN DATE IS
/******************************************************************************
   NAME:       MINUTESBEFORE
   PURPOSE:    ��ȡ��ǰϵͳʱ��n����ǰ��ʱ��
******************************************************************************/
BEGIN
   RETURN MinutesBeforeTime(SYSDATE,minutes);
END MinutesBefore;
/
CREATE OR REPLACE FUNCTION HoursBeforeNow(hours NUMBER) RETURN DATE IS
/******************************************************************************
   NAME:       HoursBefore
   PURPOSE:    ��ȡ��ǰϵͳʱ��nСʱǰ��ʱ��
******************************************************************************/
BEGIN
   RETURN HoursBeforeTime(SYSDATE,hours);
END HoursBeforeNow;
/
CREATE OR REPLACE FUNCTION HoursBefore(hours NUMBER) RETURN DATE IS
/******************************************************************************
   NAME:       HoursBefore
   PURPOSE:    ��ȡ��ǰϵͳʱ��nСʱǰ��ʱ��
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
   PURPOSE:    ���յ����ź����ONLINEMAN�������

   REVISIONS:
   Ver        Date        Author           Description
   ---------  ----------  ---------------  ------------------------------------
   1.0        2006-3-19   YuLeyuan         1. ��������Ѳ��Ա��.

   NOTES:
      �漰���ı�:  ONLINEMAN
      ����      :  keyid  SIMID��
                   gps    GPS����
                   op     ������־  1 Ѳ�� 2 �ƶ���· 3 ����
******************************************************************************/
BEGIN
   --���ȿ������Ƿ����и�simid�ļ�¼
   SELECT COUNT (simid)
     INTO n
     FROM onlineman
    WHERE simid = keyid;
   --�����¼�Ѵ��ڣ�����¸�����¼���������һ����¼
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
