
          <Route exact path="/">
            <Home />
          </Route>
          {checkAdmin}
          <Route exact path="/jobs">
            <Jobs />
          </Route>
          <Route exact path="/jobs/work/:id">
            <DetailJob />
          </Route>
          <Route exact path="