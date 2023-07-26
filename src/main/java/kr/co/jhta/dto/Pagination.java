package kr.co.jhta.dto;

import java.util.stream.IntStream;

import lombok.Getter;

@Getter
public class Pagination {
	
	private int page;
	private int size;
	private int totalElements;
	private int totalPages;
	
	private int pagesPerBlock = 5;
	private int totalBlocks;
	private int currentBlock;
	private boolean isFirst;
	private boolean isLast;
	private int prev;
	private int next;
	private int[] pageNumbers;
	
	
	public Pagination(int page, int size, int totalPages) {
		this.page = page;
		this.size = size;
		this.totalPages = totalPages;
		
		init();
	}
	
	private void init() {
	      totalBlocks = (int) Math.ceil((double) totalPages/pagesPerBlock);
	      currentBlock = (int) Math.ceil((double) (page+1)/pagesPerBlock);
	      isFirst = page == 0;
	      isLast = page == totalPages - 1;
	      prev = page - 1;
	      next = page + 1;
	      int beginPage = (currentBlock -1) * pagesPerBlock + 1;
	      int endPage = currentBlock * pagesPerBlock;
	      if (currentBlock ==  totalBlocks) {
	         endPage = totalPages;
	      }
	      pageNumbers = IntStream.range(beginPage, endPage+1).toArray();
	      
	   }
	
}
