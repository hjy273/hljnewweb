package com.cabletech.linepatrol.trouble.services;

/**
 *����ģ�鳣��
 */
public class TroubleConstant {
	public static final String TROUBLE_TYPE_SIM="0";//Ѳ��
	public static final String TROUBLE_TYPE_SYS="1";//��֪
	public static final String IS_GREAT_TROUBLE_N="0";//��
	public static final String IS_GREAT_TROUBLE_Y="1";//���ش����
	
//	public static final String OUTSKIRT="1";//����ƽ̨
//	public static final String CITY="0";//Ч��ƽ̨
	public static final String TEL_CITY="82893086 60775205";//����ƽ̨��ϵ�绰
	public static final String TEL_OUTSKIRT="60515400";//Ч��ƽ̨��ϵ�绰
	
	
    public static final String TROUBLE_WATI_REPLY = "0";//������
  //  public static final String TROUBLE_WATI_CLOSE = "1";//���ر�  �� ����ĵ��Ӳ���Ҫ��ˣ���Ҫ�ֶ��ر�
    public static final String TROUBLE_REPLY_WAIT_APPROVE = "10";//��ƽ̨��׼
    public static final String TROUBLE_FEEDBACK = "30";//����˷���
   // public static final String TROUBLE_REPLY_APPROVE_PASS = "31";//���ͨ��
    public static final String TROUBLE_REPLY_WATI_EXAM = "40";//������
    public static final String TROUBLE_END = "50";//���
   
    public static final String PROCESSUNIT_WAIT = "0";//������
    public static final String PROCESSUNIT_END = "1"; //�������
    
    
    
    public static final String REPLY_ROLE_JOIN = "0";//�����������˽�ɫЭ��
    public static final String REPLY_ROLE_HOST = "1";//�����������˽�ɫ ����

    public static final String TERMINAL_ADDRESS_CITY = "G20";//�豸�����س���
    public static final String TERMINAL_ADDRESS_OUTSKIRT = "G21";//�豸�����ؽ���
    
    //������ҳ��Ĵ�����Ա����
    public static final String REPLY_PROCESSER_RESPONSIBLE = "001";//001��ʾ������
    public static final String REPLY_PROCESSER_TEST_MAN = "002";//002��ʾ���ϲ�����Ա
    public static final String REPLY_PROCESSER_MEND_MAN = "003";//003��ʾ���½�����Ա
    
    public static final String TEMP_SAVE = "00";//��������ʱ����
    public static final String REPLY_APPROVE_WAIT = "0";//0�����������
    public static final String REPLY_APPROVE_NO = "1";//��������ʾ��˲�ͨ��
    public static final String REPLY_APPROVE_PASS = "2";//������ ��ʾ���ͨ��
   // public static final String REPLY_APPROVE_CLOSE = "3";//������ �ر�
    public static final String REPLY_APPROVE_DISPATCH = "01";//�������ȴ�ƽ̨���
    
    public static final String REPLY_EXAM_STATE_NO="0";//0��ʾδ�������֣�
    public static final String REPLY_EXAM_STATE_Y="1";//1��ʾ�Ѿ���������
    
    public static final String APPROVE_RESULT_NO = "0";//'0��ʾ��˲�ͨ��
    public static final String APPROVE_RESULT_PASS = "1";//1��ʾ���ͨ��
    public static final String APPROVE_RESULT_TRANSFER = "2";//2��ʾת��'
    
    
    public static final String APPROVE_MAN = "01";//�����
    public static final String APPROVE_TRANSFER_MAN = "02";//ת����
    public static final String APPROVE_READ = "03";//������
    
   //public static final String IS_TRANSFER_N = "N";//�Ƿ���й�ת��
   // public static final String IS_TRANSFER_Y = "Y";//�Ƿ���й�ת��
    
    public static final String LP_TROUBLE_REPLY="LP_TROUBLE_REPLY";//������
    public static final String LP_APPROVE_INFO="LP_APPROVE_INFO";//��˱�
    public static final String LP_TROUBLE_INFO="LP_TROUBLE_INFO";//���ϱ�
    public static final String LP_EVALUATE="LP_EVALUATE";//���˱�
    
    
    public static final String LP_EVALUATE_TROUBLE="02";//02��ʾΪ���Ϲ�������
    
    
    //���ϱ�
  //  public static final String MATERIAL_USE_TYPE="trouble";//����ʹ������
    
    public static final String TROUBLE_MODULE="trouble";//����ģ��
    
    //�����ֵ� 
    public static final String ASSORTMENT_TROUBLE_TYPE="lp_trouble_type";//��������
    public static final String ASSORTMENT_TERMINAL_ADDR="terminal_address";//�����豸������
    public static final String ASSORTMENT_TROUBLE_REASON="trouble_reason_id";//����ԭ��
    
    //����ָ��
    public static final String QUOTA="1";//һ�ɹ���ָ��
    public static final String QUOTA_CITY="0";//����������ָ��
    
    public static final double REPLY_RATE=5.5;//������ʱ����׼ֵ5.5��	
    public static final double GREAT_TROUBLE_TIME=24;//����������ָ�� ���Ϸ�����ʱ�ʣ��ش�24Сʱ����ͨ48Сʱ��
    public static final double COMMON_TROUBLE_TIME=48;//
    
    
    
    
    
    
    
    
}
