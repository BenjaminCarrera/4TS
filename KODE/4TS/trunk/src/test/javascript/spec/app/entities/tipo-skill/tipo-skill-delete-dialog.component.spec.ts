/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { TipoSkillDeleteDialogComponent } from 'app/entities/tipo-skill/tipo-skill-delete-dialog.component';
import { TipoSkillService } from 'app/entities/tipo-skill/tipo-skill.service';

describe('Component Tests', () => {
  describe('TipoSkill Management Delete Component', () => {
    let comp: TipoSkillDeleteDialogComponent;
    let fixture: ComponentFixture<TipoSkillDeleteDialogComponent>;
    let service: TipoSkillService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [TipoSkillDeleteDialogComponent]
      })
        .overrideTemplate(TipoSkillDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoSkillDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoSkillService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
