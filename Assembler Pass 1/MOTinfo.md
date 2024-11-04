| Mnemonic | Class | m/c code | length |
|----------|-------|----------|--------|
| STOP     | IS    | 00       | 1      |
| ADD      | IS    | 01       | 1      |
| SUB      | IS    | 02       | 1      |
| MULTI    | IS    | 03       | 1      |
| MOVER    | IS    | 04       | 1      |
| MOVEM    | IS    | 05       | 1      |
| COMB     | IS    | 06       | 1      |
| BC       | IS    | 07       | 1      |
| DIV      | IS    | 08       | 1      |
| READ     | IS    | 09       | 1      |
| PRINT    | IS    | 10       | 1      |
| END      | AD    | 01       | -      |
| START    | AD    | 02       | -      |
| ORIGIN   | AD    | 03       | -      |
| EQU      | AD    | 04       | -      |
| LTORG    | AD    | 05       | -      |
| DS       | DL    | 01       | -      |
| DC       | DL    | 02       | 1      |

---

| Operand | Register | m/c code | length |
|---------|----------|----------|--------|
| A - C   | RG       | 01-03    | -      |

---

| Condition | CC | m/c code | length |
|-----------|----|----------|--------|
| EQ, LT, GT, LE, GE, NE | CC | 01-06 | - |
