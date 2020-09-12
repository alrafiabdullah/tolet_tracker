from django.shortcuts import render, redirect
from django.contrib.auth import authenticate, login, logout, update_session_auth_hash
from django.http import HttpResponseRedirect, HttpResponse
from django.urls import reverse
from django.contrib.auth.decorators import login_required
from .forms import UserForm, EditProfile, EditDetails
from django.contrib import messages
from .models import UserProfileInfo, FlatListingInfo
from django.contrib.auth.models import User
from django.contrib.auth.forms import UserChangeForm, PasswordChangeForm

# The Initial View
def index(request):
    return render(request, 'tolet/index.html')

# Home/Dashboard View
@login_required
def home(request):
    return render(request, "tolet/home.html")

# User Registration View
def register(request):
    if request.user.is_authenticated:
        return redirect('tolet:home')
    else:
        if request.method == "POST":
            user_form = UserForm(data = request.POST)

            if user_form.is_valid():
                user = user_form.save()
                user.set_password(user.password)
                user.save()
                username = user_form.cleaned_data.get('username')
                messages.success(request, "Account created successfully for " + username +" 🎈")
                return HttpResponseRedirect(reverse('tolet:user_login'))
            else:
                print(user_form.errors)
        else:
            user_form = UserForm()
        return render(request, 'tolet/registration.html', {'user_form':user_form})

# User Login View
def user_login(request):
    if request.user.is_authenticated:
        return redirect('tolet:home')
    else:
        if request.method == 'POST':
            username = request.POST.get('username')
            password = request.POST.get('password')

            user = authenticate(username=username, password=password)

            if user is not None:
                if user.is_active:
                    login(request, user)
                    print("\nUsername: {}, Passoword: {}, Authenticated!\n".format(username, password))
                    return redirect('tolet:home')
                else:
                    print("\nUsername: {}, Account is not active!\n".format(username))
            else:
                messages.info(request, "Username or Password is incorrect")
                print("\nUsername: {}, Passoword: {}, Not Authenticated!\n".format(username, password))
                return redirect('tolet:user_login')
        else:
            return render(request, 'tolet/login.html')

# User Logout View
@login_required
def user_logout(request):
    logout(request)
    return HttpResponseRedirect(reverse('tolet:index'))

# User Profile View
@login_required
def profile(request):
    if request.method=='POST':
        form = EditProfile(request.POST, instance=request.user)

        if form.is_valid():
            form.save()
            return redirect('tolet:home')
    else:
        form = EditProfile(instance=request.user)
        context = {
            'form': form
            }
        return render(request, 'tolet/profile.html',context)

# User Listing View
@login_required
def listings(request):
    return render(request, 'tolet/listings.html')

# User Add Listing View
@login_required
def listings_form(request):
    if request.method=='POST':
        form = EditDetails(request.POST, instance=request.user)

        if form.is_valid():
            form.save()
            update_session_auth_hash(request, request.user)
            messages.success(request, "Listing has been created! 🌐")
            return redirect('tolet:listings_list')
        else:
            print(form.errors)
            messages.error(request, "Listing couldn't be posted! 🛑")
            return render(request, 'tolet/listings_form.html')
    else:
        form = EditDetails(request.POST)
        context = {
            'form':form
        }
        return render(request, 'tolet/listings_form.html', context)

# User Check Listing View
@login_required
def listings_list(request):
    form = EditDetails()
    flat_listing = FlatListingInfo.objects.all()
    
    context = {
        'form': form,
        'flats': flat_listing
    }
    return render(request, 'tolet/listings_list.html', context)

# User Settings View
@login_required
def settings(request):
    if request.method=='POST':
        form = PasswordChangeForm(data=request.POST, user=request.user)

        if form.is_valid():
            form.save()
            update_session_auth_hash(request, form.user)
            messages.success(request, "Password successfully changed for " + request.user.username + " ✅")
            return redirect('tolet:home')
        else:
            messages.error(request, "Error in password changing ❌")
            return render(request, 'tolet/settings.html')
    else:
        form = PasswordChangeForm(user=request.user)
        context = {
            'form': form
            }
        return render(request, 'tolet/settings.html', context)

# GraphQL View
def query(request):
    return render(request, 'tolet/query.html')