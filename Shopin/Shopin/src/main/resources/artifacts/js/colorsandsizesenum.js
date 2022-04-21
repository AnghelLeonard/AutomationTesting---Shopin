var COLORS = {};
COLORS['a'] = '#ffffff';
COLORS['b'] = '#0000cc';
COLORS['c'] = '#000000';
COLORS['d'] = '#d80202';
COLORS['e'] = '#ff8cf1';
COLORS['f'] = '#d8b793';
COLORS['g'] = '#5c5d60';
COLORS['h'] = '#b81f6b';
COLORS['i'] = '#099b10';
COLORS['m'] = '#750c9b';
COLORS['n'] = '#602a13';
COLORS['o'] = '#f24804';
COLORS['p'] = '#7fe6ef';
COLORS['r'] = '#a1ed09';
COLORS['s'] = '#9b8066';
COLORS['t'] = '#04f2ab';
COLORS['q'] = '#7c0909';
COLORS['x'] = '#fcf82a';


var TEXTS = {};
TEXTS['a'] = 'Alb';
TEXTS['b'] = 'Albastru';
TEXTS['c'] = 'Negru';
TEXTS['d'] = 'Rosu';
TEXTS['e'] = 'Roz';
TEXTS['f'] = 'Bej';
TEXTS['g'] = 'Gri';
TEXTS['h'] = 'Magenta';
TEXTS['i'] = 'Verde Crud';
TEXTS['m'] = 'Mov';
TEXTS['n'] = 'Maro';
TEXTS['o'] = 'Orange';
TEXTS['p'] = 'Bleu';
TEXTS['r'] = 'Verde lamaie';
TEXTS['s'] = 'Nude';
TEXTS['t'] = 'Turcoaz';
TEXTS['q'] = 'Grena';
TEXTS['x'] = 'Galben';

var SIZES = {};
// pentru casa
SIZES[1] = '_S-1_S-M-1_M-1_L-1_XL-1_';
// pantaloni si jeans
SIZES[2] = "_3XS-2_3XS-L32-2_XXS-2_XS-2_XS-S-2_S-2_S-M-2_M-2_M-L-2_L-2_L-XL-2_XL-2_XL-XXL-2_XXL-2_3XL-2_4XL-2_5XL-2_23-2_24-2_25-2_26-2_27-2_28-2_29-2_30-2_31-2_32-2_33-2_34-2_XXS-L32-2_XS-L30-2_XL-L32-2_XS-L34-2_XS-S-L32-2_XS-S-L34-2_S-L30-2_S-L32-2_S-L34-2_S-M-L32-2_S-M-L34-2_M-L30-2_M-L32-2_M-L34-2_M-L-L34-2_L-L30-2_L-L32-2_XL-L30-2_XL-L32-2_XL-L34-2_XXL-Short-2_XXL-L30-2_XL-L32-2_XL-L34-2_3XL-L32-2_W24-L27-2_W24-L28-2_W24-L30-2_W24-L32-2_W24-L34-2_W25-L26-2_W25-L27-2_W25-L28-2_W25-L29-2_W25-L30-2_W25-L31-2_W25-L34-2_W26-L26-2_W26-L27-2_W26-L28-2_W26-L29-2_W26-L30-2_W26-L31-2_W26-L32-2_W26-L33-2_W26-L34-2_W27-L26-2_W27-L27-2_W27-L28-2_W27-L29-2_W27-L30-2_W27-L31-2_W27-L32-2_W27-L34-2_W28-L26-2_W28-L27-2_W28-L28-2_W28-L29-2_W28-L30-2_W28-L31-2_W28-L32-2_W28-L34-2_W29-L26-2_W29-L27-2_W29-L28-2_W29-L29-2_W29-L30-2_W29-L31-2_W29-L32-2_W29-L36-2_W30-L26-2_W30-L27-2_W30-L28-2_W30-L29-2_W30-L30-2_W30-L31-2_W30-L32-2_W30-L34-2_W30-L36-2_W31-L32-2_W31-L34-2_W31-L36-2_W32-L28-2_W32-L29-2_W32-L30-2_W32-L32-2_W32-L34-2_W33-L29-2_W33-L30-2_W33-L32-2_W33-L34-2_W33-L38-2_W34-L29-2_W34-L31-2_W34-L32-2_W36-L32-2_W38-L32-2_W40-L32-2_W42-L32-2_";
// tricouri, bluze, camasi si jachete
SIZES[3] = "_U-3_XXS-3_XS-3_XS-S-3_S-3_S-M-3_M-3_M-L-3_L-3_L-XL-3_XL-3_XL-XXL-3_XXL-3_XXL-3XL-3_3XL-3_4XL-3_5XL-3_";
// costume de baie si articole pentru plaja
SIZES[4] = "_XXS-4_XS-4_XSqB-4_XSqC-4_XSqD-4_S-4_SqA-4_SqB-4_M-4_L-4_LqB-4_XL-4_XXL-4_XXLqB-4_3XL-4_4XL-4_65-4_65B-4_70A-4_70B-4_70C-4_70D-4_70E-4_70F-4_75-4_75A-4_75B-4_75C-4_75D-4_75E-4_75F-4_80-4_80A-4_80B-4_80C-4_80D-4_80E-4_85A-4_85B-4_85C-4_85D-4_90B-4_";
// lenjerie initma si imbracaminte de casa
SIZES[5] = "_U-5_XXS-5_XS-5_XS-S-5_S-5_S-M-5_M-5_M-L-5_L-5_L-XL-5_XL-5_XXL-5_3XL-5_4XL-5_5XL-5_19-5-22-5_23-26-5_27-30-5_31-34-5_35-38-5_36-37-5_37-39-5_37-41-5_38-39-5_39-42-5_40-41-5_42-43-5_43-46-5_47-49-5_47-50-5_65A-5_65B-5_65C-5_70A-5_70B-5_70C-5_70D-5_70DD-5_75A-5_75B-5_75C-5_75D-5_75DD-5_75E-5_80A-5_80B-5_80C-5_80D-5_80DD-5_80E-5_80F-5_85A-5_85B-5_85C-5_85D-5_85DD-5_85E-5_85F-5_90B-5_90C-5_90D-5_90E-5_90F-5_95C-5_95D-5_95E-5_95F-5_";
// fuste si rocchii
SIZES[6] = "_XXS-6_XS-6_XS-S-6_S-6_S-M-6_M-6_M-L-6_L-6_XL-6_XXL-6_3XL-6_4XL-6_5XL-6_24-6_25-6_26-6_27-6_28-6_29-6_30-6_31-6_32-6_";

var NAMES = {};
NAMES[1] = 'General';
NAMES[2] = 'Pantaloni si jeans';
NAMES[3] = 'Tricouri, bluze, camasi si jachete';
NAMES[4] = 'Costume de baie si articole pentru plaja';
NAMES[5] = 'Lenjerie initma si imbracaminte de casa';
NAMES[6] = 'Fuste si rochii';




