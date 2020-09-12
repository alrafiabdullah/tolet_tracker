from django.db import models
from phonenumber_field.modelfields import PhoneNumberField
from django.contrib.auth.models import User
from django.db.models.signals import post_save

# User Profile Model
class UserProfileInfo(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE)

    def __str__(self):
        return self.user.username

# FLat Information Model
class FlatListingInfo(models.Model):
    userprofileinfo = models.ForeignKey(UserProfileInfo, on_delete=models.CASCADE)
    phone_number = PhoneNumberField(max_length=14)
    location = models.CharField(max_length=100)
    longitude = models.FloatField(default=0.0, blank=True)
    latitude = models.FloatField(default=0.0, blank=True)
    flat_description = models.CharField(max_length=2000)
    flat_size = models.PositiveIntegerField(default=0)
    total_rent = models.PositiveIntegerField(default=0)
    post_time = models.DateTimeField(auto_now=True)
    is_active = models.BooleanField(default=True)
    photos = models.ImageField(upload_to='images/flats', blank=True)

    def __str__(self):
        return str(self.userprofileinfo.user)


def create_user(sender, **kwargs):
    if kwargs['created']:
        user_profile = UserProfileInfo.objects.create(user=kwargs['instance'])

post_save.connect(create_user, sender=User)