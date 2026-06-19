package org.example.webApplicationShopSpringBoot.util;

public class NamesUtil {


    //НАЗВАНИЯ СЕРВЛЕТОВ

    public static final String MAIN_PAGE_CLIENT = "/client/MainPage";
    public static final String MAIN_PAGE_ADMINISTRATOR = "/administrator/MainPage";
    public static final String MAIN_PAGE_OPERATOR = "/operator/MainPage";

    //BAG

    public static final String ADD_TO_BAG = "/client/AddToBag";
    public static final String CONFIRM_ORDER = "/client/ConfirmOrder";
    public static final String SHOW_BAG = "/client/ShowBag";
    public static final String CHANGE_ORDER = "/operator/ChangeOrder";
    //PRODUCT
    public static final String ADD_PRODUCT = "/administrator/AddProduct";
    public static final String ADD_PRODUCT_PAGE = "/administrator/AddProductPage";
    public static final String EDIT_CATALOG = "/administrator/EditCatalog";
    public static final String EDIT_PRODUCT = "/administrator/EditProduct";
    public static final String GET_ALL_PRODUCTS = "/GetAllProducts";
    public static final String REMOVE_PRODUCT = "/administrator/RemoveProduct";
    public static final String SHOW_CATALOG = "/client/ShowCatalog";
    public static final String UPDATE_PRODUCT = "/administrator/UpdateProduct";
    public static final String PRODUCT_PAGE = "/universal/ProductPage";
    //PRODUCT CATEGORY
    public static final String ADD_PRODUCT_CATEGORY = "/administrator/AddProductCategory";
    public static final String ADD_PRODUCT_CATEGORY_PAGE = "/administrator/AddProductCategoryPage";
    public static final String EDIT_PRODUCT_CATEGORIES = "/administrator/EditProductCategories";
    public static final String EDIT_PRODUCT_CATEGORY = "/administrator/EditProductCategory";
    public static final String GET_ALL_PRODUCT_CATEGORIES = "/GetProductCategories";
    public static final String REMOVE_PRODUCT_CATEGORY = "/administrator/RemoveProductCategory";
    public static final String UPDATE_PRODUCT_CATEGORY = "/administrator/UpdateProductCategory";
    //SELLER
    public static final String ADD_SELLER = "/administrator/AddSeller";
    public static final String ADD_SELLER_PAGE = "/administrator/AddSellerPage";
    public static final String EDIT_SELLER = "/administrator/EditSeller";
    public static final String EDIT_SELLERS = "/administrator/EditSellers";
    public static final String GET_ALL_SELLERS = "/GetAllSellers";
    public static final String REMOVE_SELLER = "/administrator/RemoveSeller";
    public static final String UPDATE_SELLER = "/administrator/UpdateSeller";

    //USER
    public static final String AUTHORIZE_USER = "AuthorizeUser";
    public static final String LOGOUT_USER = "/LogoutUser";
    public static final String SAVE_OR_UPDATE_USER = "/SaveOrUpdateUser";
    public static final String SHOW_USER_PROFILE = "/client/ShowUserProfile";

    //USER_ORDER
    public static final String GET_ALL_USER_ORDERS = "GetAllUserOrders";
    public static final String SHOW_USER_ORDERS = "/client/ShowUserOrders";
    public static final String SHOW_ORDER_POINT_ORDERS = "/operator/ShowOrderPointOrders";
    public static final String SHOW_ARRIVED_ORDER_POINT_ORDERS = "/operator/ShowArrivedOrderPointOrders";
    public static final String SHOW_ORDER_POINT_ORDER_PAGE = "/operator/ShowUserOrderPage";
    public static final String CHANGE_USER_ORDER_STATUS = "/operator/ChangeUserOrderStatus";
    public static final String SHOW_USERS_ORDER_HISTORY = "/client/ShowUsersOrderHistory";
    //НАЗВАНИЯ JSP
    //папка webapp
    public static final String ACCOUNT_CLIENT_JSP = "/account/accountClient.html";
    public static final String ACCOUNT_ADMINISTRATOR_JSP = "/account/accountAdministrator.html";
    public static final String ACCOUNT_OPERATOR_JSP = "/account/accountOperator.html";
    public static final String BAG_JSP = "/bag.html";
    public static final String CATALOG_JSP = "/catalog.html";
    public static final String LOGIN_JSP = "/login.html";
    public static final String REGISTRATION_JSP = "registration";
    public static final String USER_PROFILE_JSP = "/userProfile.html";
    //order
    public static final String ORDERS_JSP = "/order/orders.jsp";
    public static final String SHOW_ORDER_POINT_ORDERS_JSP = "/order/showOrderPointOrders.jsp";
    public static final String SHOW_ARRIVED_ORDER_POINT_ORDERS_JSP = "/order/showArrivedOrderPointOrders.jsp";
    public static final String SHOW_ORDER_POINT_ORDER_PAGE_JSP = "/order/showOrderPointOrderPage.jsp";
    public static final String SHOW_ARCHIVED_ORDERS_JSP = "/order/showArchivedOrders.jsp";
    //product
    public static final String ADD_PRODUCT_JSP = "/superUser/product/addProduct.html";
    public static final String EDIT_CATALOG_JSP = "/superUser/product/editCatalog.html";
    public static final String EDIT_PRODUCT_JSP = "/superUser/product/editProduct.html";
    public static final String PRODUCT_PAGE_JSP = "/superUser/product/productPage.html";
    //productCategory
    public static final String ADD_PRODUCT_CATEGORY_JSP = "/superUser/productCategory/addProductCategory.html";
    public static final String EDIT_PRODUCT_CATEGORIES_JSP = "/superUser/productCategory/editProductCategories.html";
    public static final String EDIT_PRODUCT_CATEGORY_JSP = "/superUser/productCategory/editProductCategory.html";
    //seller
    public static final String ADD_SELLER_PAGE_JSP = "/superUser/seller/addSellerPage.html";
    public static final String EDIT_SELLER_JSP = "/superUser/seller/editSeller.html";
    public static final String EDIT_SELLERS_JSP = "/superUser/seller/editSellers.html";


    //НАЗВАНИЯ ПАРАМЕТРОВ:
    public static final String COUNT_PARAMETER = "count";
    public static final String USER_ID_PARAMETER = "userId";
    public static final String PRODUCT_ID_PARAMETER = "productId";

    public static final String PRODUCT_NAME_PARAMETER = "productName";
    public static final String PRODUCT_CATEGORY_NAME_PARAMETER = "productCategoryName";
    public static final String PRODUCT_CATEGORY_ID_PARAMETER = "productCategoryId";

    public static final String PRICE_PARAMETER = "price";
    public static final String SELLER_PARAMETER = "seller";

    public static final String SELLER_NAME_PARAMETER = "sellerName";
    public static final String SELLER_ADDRESS_PARAMETER = "sellerAddress";
    public static final String SELLER_ID_PARAMETER = "sellerId";

    //user
    public static final String NAME_PARAMETER = "name";
    public static final String LOGIN_PARAMETER = "login";
    public static final String PASSWORD_PARAMETER = "password";
    public static final String OLD_PASSWORD_PARAMETER = "oldPassword";
    public static final String NEW_PASSWORD_PARAMETER = "newPassword";
    public static final String NEW_PASSWORD_REPEAT_PARAMETER = "newPasswordRepeat";
    public static final String BIRTHDAY_PARAMETER = "birthday";
    public static final String PAYMENT_METHODS_PARAMETER = "paymentMethods";
    public static final String CATALOG_PARAMETER = "catalog";


    public static final int PRODUCT_PER_PAGE = 6;


}
