import graphene
from tolet.schema import Query as tolet_query


class Query(tolet_query):
    pass

# API Call in Pythonic way
schema = graphene.Schema(query=Query, auto_camelcase=False)