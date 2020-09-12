import graphene
from graphene_django.types import DjangoObjectType
from .models import UserProfileInfo, FlatListingInfo


class FlatListingType(DjangoObjectType):
    class Meta():
        model = FlatListingInfo

# Returns Flat Information Query
class Query(graphene.ObjectType):
    all_flatlistinginfo=graphene.List(FlatListingType)

    def resolve_all_flatlistinginfo(self, info, **kwargs):
        return FlatListingInfo.objects.all()