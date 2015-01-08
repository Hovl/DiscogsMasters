FROM dockerfile/java:oracle-java8

RUN mkdir -p /usr/local/discogs
WORKDIR /usr/local/discogs

CMD ["bash"]