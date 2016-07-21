package org.openmrs.module.systemmonitor.indicator;

/**
 * All memory whether RAM or SWAP is in MEga Bytes (MB), this is meant to
 * support may be persisting memory to the db so that we can monitor last
 * day/week or year
 */
public class MemoryIndicator {
	/**
	 * Free RAM, in Mega Bytes
	 */
	private Long freeMemory;

	public Long getFreeMemory() {
		return freeMemory;
	}

	public void setFreeMemory(Long freeMemory) {
		this.freeMemory = freeMemory;
	}

	public Long getTotalMemory() {
		return totalMemory;
	}

	public void setTotalMemory(Long totalMemory) {
		this.totalMemory = totalMemory;
	}

	public Long getUsedMemory() {
		return usedMemory;
	}

	public void setUsedMemory(Long usedMemory) {
		this.usedMemory = usedMemory;
	}

	public Long getTotalSWAPMemory() {
		return totalSWAPMemory;
	}

	public void setTotalSWAPMemory(Long totalSWAPMemory) {
		this.totalSWAPMemory = totalSWAPMemory;
	}

	public Long getUsedSWAPMemory() {
		return usedSWAPMemory;
	}

	public void setUsedSWAPMemory(Long usedSWAPMemory) {
		this.usedSWAPMemory = usedSWAPMemory;
	}

	public Long getFreeSWAPMemory() {
		return freeSWAPMemory;
	}

	public void setFreeSWAPMemory(Long freeSWAPMemory) {
		this.freeSWAPMemory = freeSWAPMemory;
	}

	/**
	 * Total RAM, in Mega Bytes
	 */
	private Long totalMemory;

	/**
	 * Used RAM, in Mega Bytes
	 */
	private Long usedMemory;

	private Long totalSWAPMemory;

	private Long usedSWAPMemory;

	private Long freeSWAPMemory;
}
