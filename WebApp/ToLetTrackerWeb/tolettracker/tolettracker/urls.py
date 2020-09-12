"""tolettracker URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.0/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path, include
from django.contrib.auth import views as auth_views
from tolet import views
from . import  settings
from django.contrib.staticfiles.urls import static, staticfiles_urlpatterns
from graphene_django.views import GraphQLView
from .schema import schema

#URL's of Main Project
urlpatterns = [
    path('admin/', admin.site.urls),
    path('', include('tolet.urls')),

    path('graphql/', GraphQLView.as_view(
        graphiql=True,
        schema=schema
    ), name='graphql'),

    path('reset_password/', auth_views.PasswordResetView.as_view(template_name="tolet/password_reset.html"), name='reset_password'),
    path('reset_password_sent/', auth_views.PasswordResetDoneView.as_view(template_name="tolet/reset_password_sent.html"), name='password_reset_done'),
    path('reset/<uidb64>/<token>/', auth_views.PasswordResetConfirmView.as_view(template_name="tolet/password_reset_form.html"), name='password_reset_confirm'),
    path('reset_password_complete/', auth_views.PasswordResetCompleteView.as_view(template_name="tolet/password_reset_done.html"), name='password_reset_complete'),
]

urlpatterns += staticfiles_urlpatterns()
urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_DIR)