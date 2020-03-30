/**
 * @Description:    简单的区块链示例:扑克牌斗地主记账。
 * 
 * @author          Charles (yonglin_guo@hotmail.com)
 * @version         V1.0  
 * @Date            03/19/2020
 */
package com.janny.pokerblockchain;
 
import com.janny.pokerblockchain.JannyUtil;

/**
 PokerBlock 区块类
 “区块类”中存放每次区块的信息（记账信息，解密信息，链接信息等），我们在该类中存放6个值：
 data -当前区块的说明信息，第几局。
 xiaozhang - 记录小张在当前局中的输赢数量
 xiaowang - 记录小王在当前局中的输赢数量
 xiaoli - 记录小李在当前局中的输赢数量
 strDateTime - 记录当前交易发生的时间
 nonce - 记录当前区块的工作量(即通过多少次hash运算最后得到了符合条件的当前区块的哈希值)
 hash - 当前区块的哈希值
 previousHash - 前一个区块的hash值

 */
public class PokerBlock {
	

	//每个区块存放的数据信息，这里我们存放的是第几局，以及三个人的在当前牌局的输赢数量。
	private String data; 	
	private int xiaozhang; 
	private int xiaowang; 
	private int xiaoli; 
	
	
	//时间字符串
	private String strDateTime;
	
	/* 	挖矿者的工作量证明PoW。
	 *	在这里指需要经过多少次哈希运算才能得到满足条件的哈希值
	 *	
	 */	
	private int nonce;
	
	// 当前区块的哈希值;	
	public String hash;
	
	// 前一个区块的hash值;	
	public String previousHash; 
	
	
	
	//构造方法  
	public PokerBlock(String data,int xiaozhang, int xiaowang, int xiaoli, String previousHash ) {
		this.data = data;
		this.xiaozhang = xiaozhang;
		this.xiaowang = xiaowang;
		this.xiaoli = xiaoli;
		
		this.previousHash = previousHash;
		this.strDateTime = (new JannyUtil()).getCurrentDateStr();
		this.hash = calculateHash(); 
	}

	
	//根据当前区块的信息内容计算新的哈希值
	public String calculateHash() {
		String calculatedhash = (new JannyUtil()).applySha256( 
				data + 
				Integer.toString(xiaozhang) + 
				Integer.toString(xiaowang) + 
				Integer.toString(xiaoli) + 
				strDateTime +
				Integer.toString(nonce) + 
				previousHash
				);
		return calculatedhash; 
	}
	
	/* “挖矿”过程
	在这里指需要经过多少次哈希运算才能得到满足条件的哈希值
	条件有参数 difficulty 设定
	例如：如果difficulty 值是2,则要求计算出来的哈希值前2位字节为“00”
	如果计算出来不是，则nonce 值加1，继续计算
	直到算出来的哈希值满足条件，则结束 
	*/
	public void mineBlock(int difficulty) {
		//难度值，difficulty越大，计算量越大
		String target = (new JannyUtil()).getDificultyString(difficulty);
		//difficulty如果为4，那么target则为 0000
		while(!hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			hash = calculateHash();
			System.out.println("!PoW工作中..." + "nonce:"+ nonce + ":hash: "+ hash);
		}
		System.out.println("!!!当前节点PoW完成，新的区块被创建:" + hash);
	}
	
}