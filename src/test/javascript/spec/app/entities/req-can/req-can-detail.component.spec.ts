/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { ReqCanDetailComponent } from 'app/entities/req-can/req-can-detail.component';
import { ReqCan } from 'app/shared/model/req-can.model';

describe('Component Tests', () => {
  describe('ReqCan Management Detail Component', () => {
    let comp: ReqCanDetailComponent;
    let fixture: ComponentFixture<ReqCanDetailComponent>;
    const route = ({ data: of({ reqCan: new ReqCan(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [ReqCanDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ReqCanDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReqCanDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.reqCan).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
