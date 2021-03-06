package iphonex;

import java.util.Vector;

public class ReqManager {
	public Mobile mobile;
	public Vector<IPhoneX> iphonexVec;

	private volatile int curSendMsgReqNum; //已请求数目
	private volatile int curSendMsgFinishNum;  //已完成数目
	private volatile int curSendMsgSucNum; //已成功数目
	private volatile int maxParaSendMsgReqNum; //最大并发请求

	private volatile int curLoginReqNum;
	private volatile int curLoginFinishNum;
	private volatile int curLoginSucNum;
	private volatile int maxParaLoginReqNum;

	private volatile int curBuyReqNum;
	private volatile int curBuyFinishNum;
	private volatile int curBuySucNum;
	private volatile int maxParaBuyReqNum;

	public ReqManager(Mobile mobile){
		this.mobile = mobile;
		iphonexVec = new Vector<>();
	}
	public void startSendMsg(int maxParaReqNum){
		this.maxParaSendMsgReqNum = maxParaReqNum;
		sendMsg(-1, "", "");
	}
	public synchronized void sendMsg(int id, String verifyCode, String state){
		if(id >= 0){
			if (state.equals("发送验证码成功")){
				curSendMsgSucNum ++;
				iphonexVec.get(id).setVerifyCode(verifyCode);
				mobile.updateSendMsgState(curSendMsgSucNum, curSendMsgReqNum);
			}
			mobile.updateTableState(id, state);
			curSendMsgFinishNum ++;
		}
		int diff = maxParaSendMsgReqNum - (curSendMsgReqNum - curSendMsgFinishNum);
		while(curSendMsgReqNum < iphonexVec.size() && diff>0){
			this.iphonexVec.get(curSendMsgReqNum).sendMsg();
			curSendMsgReqNum ++;
			diff--;
		}

	}
	public  void startLogin(int maxParaReqNum){
		this.maxParaLoginReqNum = maxParaReqNum;
		login(-1, "", "");
	}
	public synchronized void login(int id, String result, String state){
		if(id >= 0){
			if (state.equals("登录成功")){
				curLoginSucNum ++;
				iphonexVec.get(id).setCookie(result);
				mobile.updateLoginState(curLoginSucNum, curLoginReqNum);
			}
			mobile.updateTableState(id, state);
			curLoginFinishNum ++;
		}
		int diff = maxParaLoginReqNum - (curLoginReqNum - curLoginFinishNum);
		while(curLoginReqNum < iphonexVec.size() && diff > 0){
			this.iphonexVec.get(curLoginReqNum).login();
			curLoginReqNum ++;
			diff --;
		}

	}
	public void startBuy(int maxParaReqNum, int prodID, int modelID){
		this.maxParaBuyReqNum = maxParaReqNum;
		buy(-1, "", "");
	}
	public synchronized void buy(int id, String result, String state){
		if(id >= 0){
			if (state.equals("购买成功")){
				curBuySucNum ++;
				mobile.updateBuyState(curBuySucNum, curBuyReqNum);
			}
			mobile.updateTableState(id, state);
			curBuyFinishNum ++;
		}
		int diff = maxParaBuyReqNum - (curBuyReqNum - curBuyFinishNum);
		while(curBuyReqNum < iphonexVec.size() && diff > 0){
			this.iphonexVec.get(curBuyReqNum).buy();
			curBuyReqNum ++;
			diff --;
		}

	}

}
