/**
 * @Description:    简单的区块链示例:扑克牌斗地主记账。
 * 
 * @author          Charles (yonglin_guo@hotmail.com)
 * @version         V1.0  
 * @Date            03/19/2020
 */
 
package com.janny.pokerblockchain;


import java.util.ArrayList;
import java.util.Base64;
 
import java.security.Security;

//需要下载并确保 gson-2.8.6.jar 在类路径中
import com.google.gson.GsonBuilder;


/**
 * 区块链类
 * @author Charles
 *
 */
public class PokerBlockChain{
	//存放所有的区块集合
	public static ArrayList<PokerBlock> blockchain = new ArrayList<PokerBlock>();
	
	//挖矿的难度，数字越大越难
	public static int difficulty = 4;
 
	public static void main(String[] args) {	
	
		String title = "创世块";
		int xiaozhang = 0;
		int xiaowang = 0;
		int xiaoli = 0;
		System.out.println("开始创建第0个区块链:创世块....... ");
		addBlock(new PokerBlock("第0个区块",xiaozhang,xiaowang,xiaoli,"0"));//创世块

		title = "第1局";
		xiaozhang = 1;
		xiaowang = 1;
		xiaoli = -2;
		System.out.println("正在创建第1个区块....... ");
		addBlock(new PokerBlock("第1个区块",xiaozhang,xiaowang,xiaoli,blockchain.get(blockchain.size()-1).hash));
		

		title = "第2局";
		xiaozhang = 6;
		xiaowang = -3;
		xiaoli = -3;
		System.out.println("正在创建第2个区块....... ");
		addBlock(new PokerBlock("第2个区块",xiaozhang,xiaowang,xiaoli,blockchain.get(blockchain.size()-1).hash));


		title = "第3局";
		xiaozhang = -4;
		xiaowang = 2;
		xiaoli = 2;
		System.out.println("正在创建第3个区块....... ");
		addBlock(new PokerBlock("第3个区块",xiaozhang,xiaowang,xiaoli,blockchain.get(blockchain.size()-1).hash));
		
		System.out.println("区块链是否有效的: " + isChainValid());
		
		String blockchainJson = (new JannyUtil()).getJson(blockchain);
		System.out.println(blockchainJson);
	
	}
	
	/**
	 * 检查区块链的完整性
	 * 从第一个区块开始逐次检查两个内容：
		1，当前的区块哈希值是否正确？（通过重新计算结果，和区块中保存的区块比较）
		2，当前区块中保存的“前区块哈希值” 是不是和前一个区块中的实际的哈希值相等
	 */
	public static Boolean isChainValid() {
		PokerBlock currentBlock; 
		PokerBlock previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		//循环区块链检查哈希值:
		for(int i=1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			
			//比较当前区块中的哈希值是否和重新计算出来的相等？
			if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
				System.out.println("当前区块哈希值校验错误...");			
				return false;
			}
			//当前区块中保存的“前区块哈希值” 是不是和前一个区块中的实际的哈希值是否相等？
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				System.out.println("当前区块的‘前哈希值’校验错误...");
				return false;
			}
			//检查当前区块是否已经加到区块链上？
			if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
				System.out.println("这个区块还没有被加到区块链上...");
				return false;
			}
			
		}
		return true;
	}
	/**
	 * 在区块链上增加一个新的区块
	 * @param newBlock
	 */
	public static void addBlock(PokerBlock newBlock) {
		newBlock.mineBlock(difficulty);
		blockchain.add(newBlock);
	}
}