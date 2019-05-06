package com.digitaldestino.interfaces;

import com.digitaldestino.modelClass.add_card.AddCardResponse;
import com.digitaldestino.modelClass.add_cart.AddCartResponse;
import com.digitaldestino.modelClass.addin_queue.AddQueueResponse;
import com.digitaldestino.modelClass.apply_coupan.ApplyCoupanResponse;
import com.digitaldestino.modelClass.article.BaseResponse;
import com.digitaldestino.modelClass.articleDetail.ArticleDetailResponse;
import com.digitaldestino.modelClass.auto_complete.AutoCompleteResponse;
import com.digitaldestino.modelClass.book_table.BookTableResponse;
import com.digitaldestino.modelClass.delete_card.DeleteCardResponse;
import com.digitaldestino.modelClass.delete_cart.DeleteCartResponse;
import com.digitaldestino.modelClass.dishes_detail.DishesDetailResponse;
import com.digitaldestino.modelClass.fav_dishes.FavDishesResponse;
import com.digitaldestino.modelClass.follower.FollowerResponse;
import com.digitaldestino.modelClass.forgotpassword.ForgotResponse;
import com.digitaldestino.modelClass.getLoyalty.GetLoyaltyResponse;
import com.digitaldestino.modelClass.get_card.GetCardResponse;
import com.digitaldestino.modelClass.get_cart.GetCartResponse;
import com.digitaldestino.modelClass.get_favourite.GetFavResponse;
import com.digitaldestino.modelClass.get_feedback.GetFeedbackResponse;
import com.digitaldestino.modelClass.get_restaurant_menu.MenuResponse;
import com.digitaldestino.modelClass.getuser.UserResponse;
import com.digitaldestino.modelClass.login.LoginResponse;
import com.digitaldestino.modelClass.logout.LogoutResponse;
import com.digitaldestino.modelClass.loyalty.LoyaltyResponse;
import com.digitaldestino.modelClass.my_order.OrderResponse;
import com.digitaldestino.modelClass.near_restaurent.RestaurentResponse;
import com.digitaldestino.modelClass.notification.NotificationResponse;
import com.digitaldestino.modelClass.otp.OtpResponse;
import com.digitaldestino.modelClass.pay.PaymentResponse;
import com.digitaldestino.modelClass.rating_response.RatingResponse;
import com.digitaldestino.modelClass.resetpwd.ResetPwdResponse;
import com.digitaldestino.modelClass.restaurant_info.InfoResponse;
import com.digitaldestino.modelClass.restaurent.RestaurentResponses;
import com.digitaldestino.modelClass.send_feedback.FeedbackResponse;
import com.digitaldestino.modelClass.signup.SignUpResponse;
import com.digitaldestino.modelClass.static_pages.PrivacyResponse;
import com.digitaldestino.modelClass.update_cart.UpdateCartResponse;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("seeker/article")
    io.reactivex.Observable<BaseResponse> getArticleList();

    @POST("seeker/articleDetail")
    io.reactivex.Observable<ArticleDetailResponse> getArticleDetail(@Body Map<String, String> map);

    @POST("seeker/register")
    io.reactivex.Observable<SignUpResponse> getSignupDetail(@Body Map<String, String> map);

    @POST("seeker/register")
    io.reactivex.Observable<OtpResponse> getOtpDetails(@Body Map<String, String> map);

    @POST("seeker/authenticate")
    io.reactivex.Observable<LoginResponse> getLoginDetails(@Body Map<String, String> map);

    @POST("seeker/staticPages")
    io.reactivex.Observable<PrivacyResponse> getStaticsDetails(@Body Map<String, String> map);

    @POST("seeker/forgetpassword")
    io.reactivex.Observable<ForgotResponse> getForgotPassDetails(@Body Map<String, String> map);

    @POST("seeker/resetpassword")
    io.reactivex.Observable<ResetPwdResponse> getResetPwdDetails(@Body Map<String, String> map);

    @POST("seeker/nearByRestaurant")
    io.reactivex.Observable<RestaurentResponse> getRestaurentDetails(@Body Map<String, String> map);

    @POST("seeker/nearByRestaurant")
    io.reactivex.Observable<RestaurentResponses> getRestaurentsDetails(@Body Map<String, String> map);

    @POST("seeker/logout")
    io.reactivex.Observable<LogoutResponse> getLogoutDetails(@Body Map<String, String> map);

    @POST("seeker/changepassword")
    io.reactivex.Observable<LoginResponse> getChangePwdDetails(@Body Map<String, String> map);

    @POST("seeker/getuser")
    io.reactivex.Observable<UserResponse> getUserDetails(@Body Map<String, String> map);

    @POST("seeker/updateUser")
    io.reactivex.Observable<UserResponse> getUpdateUser(@Body Map<String, String> map);

    @HTTP(method = "DELETE", path = "seeker/delete", hasBody = true)
    io.reactivex.Observable<UserResponse> deleteUser(@Body Map<String, String> map);

    @POST("seeker/dish")
    io.reactivex.Observable<DishesDetailResponse> getDishesDetail(@Body Map<String, String> map);

    @POST("seeker/cart")
    io.reactivex.Observable<AddCartResponse> addCart(@Body Map<String, String> map);

    @POST("seeker/getCart")
    io.reactivex.Observable<GetCartResponse> getCart(@Body Map<String, String> map);

    @HTTP(method = "DELETE", path = "seeker/deletecart", hasBody = true)
    io.reactivex.Observable<DeleteCartResponse> deleteCartData(@Body Map<String, String> map);

    @POST("seeker/updatecart")
    io.reactivex.Observable<UpdateCartResponse> updateCart(@Body Map<String, String> map);

    @POST("seeker/deleteallcart")
    io.reactivex.Observable<AddCartResponse> deleteAllCart(@Body Map<String, String> map);

    @POST("seeker/bookatable")
    io.reactivex.Observable<BookTableResponse> bookTable(@Body Map<String, String> map);

    @POST("seeker/gettable")
    io.reactivex.Observable<BookTableResponse> getBookTable(@Body Map<String, String> map);

    @POST("payment/addcard")
    io.reactivex.Observable<AddCardResponse> addCard(@Body Map<String, String> map);

    @POST("payment/listcards")
    io.reactivex.Observable<GetCardResponse> getCard(@Body Map<String, String> map);

    @HTTP(method = "DELETE", path = "payment/deletecard", hasBody = true)
    io.reactivex.Observable<DeleteCardResponse> deleteCard(@Body Map<String, String> map);

    @POST("seeker/booknow")
    io.reactivex.Observable<PaymentResponse> orderPayment(@Body Map<String, String> map);

    @POST("seeker/booking")
    io.reactivex.Observable<OrderResponse> getOrder(@Body Map<String, String> map);

    @POST("seeker/sendAppFeedback")
    io.reactivex.Observable<FeedbackResponse> sendFeedback(@Body Map<String, String> map);

    @POST("seeker/sendFeedback")
    io.reactivex.Observable<RatingResponse> rateRestaurant(@Body Map<String, String> map);

    @POST("seeker/feedback")
    io.reactivex.Observable<GetFeedbackResponse> getFeedback(@Body Map<String, String> map);

    @POST("seeker/getMenuDetails")
    io.reactivex.Observable<MenuResponse> getMenu(@Body Map<String, String> map);

    @POST("seeker/getSpecialDish")
    io.reactivex.Observable<MenuResponse> getSpecialDish(@Body Map<String, String> map);

    @POST("seeker/getNewDish")
    io.reactivex.Observable<MenuResponse> getNewDish(@Body Map<String, String> map);

    @POST("seeker/getPopularDish")
    io.reactivex.Observable<MenuResponse> getPopularDish(@Body Map<String, String> map);

    @POST("seeker/follow")
    io.reactivex.Observable<FollowerResponse> followRestaurant(@Body Map<String, String> map);

    @POST("seeker/restaurantDetails")
    io.reactivex.Observable<InfoResponse> restaurantDetails(@Body Map<String, String> map);

    @POST("seeker/favouriteDish")
    io.reactivex.Observable<FavDishesResponse> favouriteDish(@Body Map<String, String> map);

    @POST("seeker/getFavourites")
    io.reactivex.Observable<GetFavResponse> getFavourites(@Body Map<String, String> map);

    @POST("seeker/autoComplete")
    io.reactivex.Observable<AutoCompleteResponse> autoComplete(@Body Map<String, String> map);

    @POST("seeker/addInQueue")
    io.reactivex.Observable<AddQueueResponse> addInQueue(@Body Map<String, String> map);

    @POST("seeker/getNotifications")
    io.reactivex.Observable<NotificationResponse> getNotifications(@Body Map<String, String> map);

    @POST("seeker/checkPromocode")
    io.reactivex.Observable<ApplyCoupanResponse> checkPromocode(@Body Map<String, String> map);

    @POST("seeker/socialLogin")
    io.reactivex.Observable<LoginResponse> socialLogin(@Body Map<String, String> map);

    @POST("seeker/addInLoyality")
    io.reactivex.Observable<LoyaltyResponse> addInLoyality(@Body Map<String, String> map);

    @POST("seeker/getLoyality")
    io.reactivex.Observable<GetLoyaltyResponse> getLoyality(@Body Map<String, String> map);
}
