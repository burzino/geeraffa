""" Creates a TestCase which adds a .soup object to responses. """

from django.test.client import Client as djangoClient
from django.test import TestCase as djangoTestCase
from bs4 import BeautifulSoup

try:
    basestring
except NameError:
    basestring = str


class Client(djangoClient):

    """ Custom client which creates BeautifulSoup object for responses. """

    def request(self, **request):
        """ Request a response from the server using GET. Return response.

        Response has .soup attribute

        """
        response = super(Client, self).request(**request)
        try:
            response.soup = BeautifulSoup(response.content, "html.parser")
        except:
            response.soup = None
        return response


class TestCase(djangoTestCase):

    """ A testcase which uses the custom BeautifulSoup client. """

    client_class = Client
