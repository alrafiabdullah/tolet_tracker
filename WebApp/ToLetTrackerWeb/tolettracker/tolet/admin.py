from django.contrib import admin
from .models import UserProfileInfo, FlatListingInfo

# Register your models here.
admin.site.site_header="ToLet Tracker Dashboard"

admin.site.register(UserProfileInfo)
admin.site.register(FlatListingInfo)

