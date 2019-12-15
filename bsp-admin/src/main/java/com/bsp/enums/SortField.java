package com.bsp.enums;


/**
 * 排序字段枚举
 * @author Hayate
 *
 */
public interface SortField {
	
	/**
	 * 获取排序列
	 * 
	 * @return 排序列名
	 */
	public String getColumnName();

	public enum EmployeeSortField implements SortField {
		ID("id"), NAME("name"), EMAIL("email"), BIRTHDATE("birthdate"), ISADMIN("isAdmin"), DEPARTMENT("department_id");

		private String columnName;

		private EmployeeSortField(String columnName) {
			this.columnName = columnName;
		}

		@Override
		public String getColumnName() {
			return this.columnName;
		}
	}
}
