from django.urls import path
from tolet import views

# URL's that's within the "tolet" app
app_name = "tolet"
urlpatterns = [
    path('', views.index, name='index'),
    path('register/', views.register, name='register'),
    path('user_login/', views.user_login, name='user_login'),
    path('home/', views.home, name='home'),
    path('user_logout/', views.user_logout, name='user_logout'),
    path('home/profile/', views.profile, name='profile'),
    path('home/listings/', views.listings, name='listings'),
    path('home/listings/listings_form/', views.listings_form, name='listings_form'),
    path('home/listings/listings_list/', views.listings_list, name='listings_list'),
    path('home/settings/', views.settings, name='settings'),
    path('graphql/query/', views.query, name='query'),
]