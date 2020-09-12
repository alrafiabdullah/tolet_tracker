from django import forms
from django.contrib.auth.models import User
from django.contrib.auth.forms import UserChangeForm 
from .models import UserProfileInfo, FlatListingInfo

# User Details Form
class UserForm(forms.ModelForm):
    password = forms.CharField(widget=forms.PasswordInput())

    class Meta():
        model = User
        fields = ('username', 'email', 'password',)


class EditProfile(UserChangeForm):
    class Meta():
        model=User
        fields = ('first_name', 'last_name', 'email',)
        exclude = ()

# FLat Information Form
class EditDetails(forms.ModelForm):
    class Meta():
        model = FlatListingInfo
        fields = ('phone_number', 'location', 'longitude', 'latitude', 'flat_description', 'flat_size', 'total_rent', 'is_active', 'photos',)