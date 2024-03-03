package pl.fboro.finanse.database

enum class SortType {
    SPENDING_YEAR_MONTH_DAY,
    SPENDING_YEAR_MONTH_DAY_ASC,
    SPENDING_AMOUNT,
    SPENDING_AMOUNT_ASC,
    INCOME_YEAR_MONTH_DAY,
    INCOME_YEAR_MONTH_DAY_ASC,
    INCOME_AMOUNT,
    INCOME_AMOUNT_ASC,
}
enum class InvestmentSortType {
    DATE,
    DATE_ASC,
    PROFIT,
    PROFIT_ASC,
}